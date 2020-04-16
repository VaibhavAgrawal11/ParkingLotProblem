package com.bridgelabz.servicetest;

import com.bridgelabz.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class test {
    ParkingLotSystem parkingLotSystem = null;
    Object vehicle = null;
    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        vehicle = new Object();
    }

    @Test
    public void givenVehicle_BeenParked_ShouldReturnTrue() {
        boolean isParked = parkingLotSystem.parkVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenLotIsNotEmpty_ShouldReturnFalse() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean isParked = parkingLotSystem.parkVehicle(new Object());
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        parkingLotSystem.parkVehicle(vehicle);
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle);
        Assert.assertTrue(isUnParked);
    }

    @Test
    public void givenVehicle_WhenNotParked_ShouldNotBeUnParked() {
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle);
        Assert.assertFalse(isUnParked);
    }
}
