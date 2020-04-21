package com.bridgelabz.servicetest;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.service.ParkingLotSystem;
import com.bridgelabz.utilities.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParkingLotServiceTest {
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    ParkingAttendant attendant = null;
    ParkingBill parkingBill = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem(100, 1);
        vehicle = new Vehicle(Vehicle.Driver.NORMAL);
        attendant = new ParkingAttendant("xyz");
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
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle, 1, 1, 4);
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
            parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
            parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
            parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
            parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
            parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Parking lot is full.", e.getMessage());
        }
    }

    @Test
    public void givenVehicles_WhenExactParkingIsDone_ShouldReturnTrue() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(2, 1);
        parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
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
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 3);
        Assert.assertEquals(owner.getSign(), Owner.Sign.PARKING_IS_FULL);
    }

    @Test
    public void givenLatestVehicle_ShouldParkAtTheNearestEmptySpace() throws ParkingLotException {
        parkingLotSystem.parkVehicle(vehicle, 1);
        parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        parkingLotSystem.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
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
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 1);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        Assert.assertEquals(true, securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingNotLotFull_SecurityShouldNotBeCalled() throws ParkingLotException {
        AirportSecurityPersonal securityPersonal = new AirportSecurityPersonal();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(securityPersonal);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        Assert.assertEquals(false, securityPersonal.redirectSecurityStaff());
    }

    @Test
    public void givenParkingLotFull_WhenVehicleUnParked_OwnerShouldRemoveFullSign() throws ParkingLotException {
        Owner owner = new Owner();
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(3, 1);
        parkingLotSystem1.register(owner);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
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
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 2);
        parkingLotSystem1.parkVehicle(new Vehicle(Vehicle.Driver.NORMAL), 3);
        boolean isUnParked = parkingLotSystem1.unParkVehicle(vehicle,
                attendant.getMyParkingSlot(vehicle),
                attendant.getLotNumber(vehicle),
                3);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenDriverIsHandicap_VehicleShouldBeParkedNearestEmptySlot() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(4, 2);
        Vehicle vehicle = new Vehicle(Vehicle.Driver.HANDICAP);
        parkingLotSystem1.parkVehicle(vehicle, 2);
        Integer getParkingLot = attendant.getLotNumber(vehicle);
        Integer getParkingSlot = attendant.getMyParkingSlot(vehicle);
        Assert.assertEquals("1 1", getParkingLot + " " + getParkingSlot);
    }

    @Test
    public void givenVehicleTypeIsLarge_ShouldParkedAtMostFreeSpace() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(4, 2);
        Vehicle vehicle1 = new Vehicle(Vehicle.VehicleType.LARGE);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(vehicle1, 2);
        Integer getParkingLot = attendant.getLotNumber(vehicle1);
        Integer getParkingSlot = attendant.getMyParkingSlot(vehicle1);
        Assert.assertEquals("1 1", getParkingLot + " " + getParkingSlot);
    }

    @Test
    public void givenVehicleColour_PoliceShouldGetTheListOfVehicleLocation() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(8, 2);
        Vehicle vehicle1 = new Vehicle("white");
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(vehicle1, 2);
        parkingLotSystem1.parkVehicle(new Vehicle("black"), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("white"), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("black"), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("red"), 1);
        PoliceDepartment police = new PoliceDepartment(parkingLotSystem1);
        List list = police.getColouredVehicleList("white");
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void givenVehicle_WhenCompanyNameProvided_ShouldReturnListOfVehicles() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(8, 2);
        Vehicle vehicle1 = new Vehicle("blue", "GJ06KL7860", "Toyota", attendant.attendantName);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(vehicle1, 2);
        parkingLotSystem1.parkVehicle(new Vehicle("blue", "GJ06KL7860", "Toyota", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("red", "GJ06KL1456", "Toyota", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("blue", "GJ06KL9008", "Toyota", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("white", "GJ06KL3845", "Toyota", attendant.attendantName), 1);
        PoliceDepartment police = new PoliceDepartment(parkingLotSystem1);
        List list = police.getDetailsOfParticularTypeOfVehicle("Toyota", "blue");
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void givenVehicle_WhenBMW_ShouldReturnList() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem1 = new ParkingLotSystem(8, 3);
        Vehicle vehicle1 = new Vehicle("blue", "GJ06KL7860", "BMW", attendant.attendantName);
        parkingLotSystem1.parkVehicle(vehicle, 1);
        parkingLotSystem1.parkVehicle(vehicle1, 2);
        parkingLotSystem1.parkVehicle(new Vehicle("blue", "GJ06KL7860", "Swift", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("red", "GJ06KL1456", "BMW", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("blue", "GJ06KL9008", "Toyota", attendant.attendantName), 1);
        parkingLotSystem1.parkVehicle(new Vehicle("white", "GJ06KL3845", "BMW", attendant.attendantName), 1);
        PoliceDepartment police = new PoliceDepartment(parkingLotSystem1);
        List list = police.getSecurityWhenGivenExpensiveCarCountIncreases("BMW");
        Assert.assertEquals(3, list.size());
    }
}