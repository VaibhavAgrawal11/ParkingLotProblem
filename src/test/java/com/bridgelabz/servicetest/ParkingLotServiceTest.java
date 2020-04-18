package com.bridgelabz.servicetest;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.service.ParkingLotSystem;
import com.bridgelabz.utilities.AirportSecurityPersonal;
import com.bridgelabz.utilities.Owner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotServiceTest {
    ParkingLotSystem parkingLotSystem = null;
    Object vehicle = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(100);
        vehicle = new Object();
    }

    @Test
    public void givenVehicle_BeenParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle);
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicle_WhenNotParked_ShouldNotBeUnParked() {
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle);
        Assert.assertFalse(isUnParked);
    }

    @Test
    public void givenVehicles_WhenParkingFull_ShouldThrowException() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(5);
        try {
            parkingLotSystem.parkVehicle(vehicle);
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(new Object());
            parkingLotSystem.parkVehicle(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full.", e.getMessage());
        }
    }

    @Test
    public void givenVehicles_WhenExactParkingIsDone_ShouldReturnTrue() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2);
        parkingLotSystem.parkVehicle(new Object());
        parkingLotSystem.parkVehicle(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true,isParked);
    }

    @Test
    public void givenParkingLotFull_OwnerShouldShowFullSign() throws ParkingLotException {
        Owner owner = new Owner();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3);
        parkingLotSystem1.register(owner);
        parkingLotSystem1.parkVehicle(vehicle);
        parkingLotSystem1.parkVehicle(new Object());
        parkingLotSystem1.parkVehicle(new Object());
        Assert.assertEquals(owner.getSign(), Owner.Sign.PARKING_IS_FULL);
    }

    @Test
    public void givenParkingLotFull_SecurityShouldBeCalled() throws ParkingLotException {
        AirportSecurityPersonal securityPersonal = new AirportSecurityPersonal();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3);
        parkingLotSystem1.register(securityPersonal);
        parkingLotSystem1.parkVehicle(vehicle);
        parkingLotSystem1.parkVehicle(new Object());
        parkingLotSystem1.parkVehicle(new Object());
        Assert.assertEquals(true,securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingNotLotFull_SecurityShouldNotBeCalled() throws ParkingLotException {
        AirportSecurityPersonal securityPersonal = new AirportSecurityPersonal();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3);
        parkingLotSystem1.register(securityPersonal);
        parkingLotSystem1.parkVehicle(vehicle);
        parkingLotSystem1.parkVehicle(new Object());
        Assert.assertEquals(false,securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingLotFull_WhenVehicleUnParked_OwnerShouldRemoveFullSign() throws ParkingLotException {
        Owner owner = new Owner();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3);
        parkingLotSystem1.register(owner);
        parkingLotSystem1.parkVehicle(vehicle);
        parkingLotSystem1.parkVehicle(new Object());
        parkingLotSystem1.parkVehicle(new Object());
        parkingLotSystem1.unParkVehicle(vehicle);
        Assert.assertEquals(owner.getSign(), null);
    }
}
