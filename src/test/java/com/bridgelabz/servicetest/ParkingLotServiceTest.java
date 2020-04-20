package com.bridgelabz.servicetest;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.service.ParkingLotSystem;
import com.bridgelabz.utilities.AirportSecurityPersonal;
import com.bridgelabz.utilities.Owner;
import com.bridgelabz.utilities.ParkingAttendant;
import com.bridgelabz.utilities.ParkingBill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotServiceTest {
    ParkingLotSystem parkingLotSystem = null;
    Object vehicle = null;
    ParkingAttendant attendant = null;
    ParkingBill parkingBill = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(100, 1);
        vehicle = new Object();
        attendant = new ParkingAttendant();
        parkingBill = new ParkingBill();
    }

    @Test
    public void givenVehicle_BeenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle, 1);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle, 1);
        Integer getSlot = attendant.getMyParkingSlot(vehicle);
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle, getSlot, 1, 4);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicle_WhenNotParked_ShouldNotBeUnParked() {
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle, attendant.getMyParkingSlot(vehicle), 1, 4);
        Assert.assertFalse(isUnParked);
    }

    @Test
    public void givenVehicle_WhenAlreadyParked_ShouldNotBeParkedAgain() {
        try {
            parkingLotSystem.parkVehicle(vehicle, 1);
            parkingLotSystem.parkVehicle(vehicle, 2);
        } catch (ParkingLotException e) {
            Assert.assertEquals(e.type, ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT);
        }
    }

    @Test
    public void givenVehicles_WhenParkingFull_ShouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5, 1);
        try {
            parkingLotSystem.parkVehicle(vehicle, 1);
            parkingLotSystem.parkVehicle(new Object(), 1);
            parkingLotSystem.parkVehicle(new Object(), 1);
            parkingLotSystem.parkVehicle(new Object(), 1);
            parkingLotSystem.parkVehicle(new Object(), 1);
            parkingLotSystem.parkVehicle(new Object(), 1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full.", e.getMessage());
        }
    }

    @Test
    public void givenVehicles_WhenExactParkingIsDone_ShouldReturnTrue() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 1);
        parkingLotSystem.parkVehicle(new Object(), 1);
        parkingLotSystem.parkVehicle(vehicle, 1);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenParkingLotFull_OwnerShouldShowFullSign() throws ParkingLotException {
        Owner owner = new Owner();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(owner);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        parkingLotSystem1.parkVehicle(new Object(), 3);
        Assert.assertEquals(owner.getSign(), Owner.Sign.PARKING_IS_FULL);
    }

    @Test
    public void givenLatestVehicle_ShouldParkAtTheNearestEmptySpace() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle, 1);
        parkingLotSystem.parkVehicle(new Object(), 2);
        parkingLotSystem.parkVehicle(new Object(), 2);
        parkingLotSystem.unParkVehicle(vehicle, attendant.getMyParkingSlot(vehicle), 1, 2);
        parkingLotSystem.parkVehicle(vehicle, 3);
        Integer parkingSlot = attendant.getMyParkingSlot(vehicle);
        Assert.assertEquals((Integer) 1, parkingSlot);
    }

    @Test
    public void givenParkingLotFull_SecurityShouldBeCalled() throws ParkingLotException {
        AirportSecurityPersonal securityPersonal = new AirportSecurityPersonal();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(securityPersonal);
        parkingLotSystem1.parkVehicle(vehicle, 2);
        parkingLotSystem1.parkVehicle(new Object(), 1);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        Assert.assertEquals(true, securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingNotLotFull_SecurityShouldNotBeCalled() throws ParkingLotException {
        AirportSecurityPersonal securityPersonal = new AirportSecurityPersonal();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(securityPersonal);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        Assert.assertEquals(false, securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingLotFull_WhenVehicleUnParked_OwnerShouldRemoveFullSign() throws ParkingLotException {
        Owner owner = new Owner();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(owner);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        parkingLotSystem1.unParkVehicle(vehicle, attendant.getMyParkingSlot(vehicle), 1, 3);
        Assert.assertEquals(owner.getSign(), null);
    }

    @Test
    public void givenParkingTime_ParkingBillShouldBeGenerated() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle, 1);
        Integer parkingSlot = attendant.getMyParkingSlot(vehicle);
        parkingLotSystem.unParkVehicle(vehicle, parkingSlot, 1, 4);
        Assert.assertEquals(7.5, parkingBill.generateParkingBill(), 0);
    }

    @Test
    public void givenMultipleParkingLots_WhenVehicleUnParked_ShouldGetLotNumber() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(4, 2);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        parkingLotSystem1.parkVehicle(new Object(), 2);
        parkingLotSystem1.parkVehicle(new Object(),3);
        boolean isUnParked = parkingLotSystem1.unParkVehicle(vehicle,
                attendant.getMyParkingSlot(vehicle),
                attendant.getLotNumber(vehicle),
                3);
        Assert.assertEquals(true,isUnParked);
    }
}