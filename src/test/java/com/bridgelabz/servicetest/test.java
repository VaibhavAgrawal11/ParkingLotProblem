package com.bridgelabz.servicetest;

import com.bridgelabz.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class test {
    ParkingLotSystem parkingLotSystem = null;
    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
    }

    @Test
    public void givenVehicle_BeenParked_ShouldReturnTrue() {
        boolean isParked = parkingLotSystem.parkVehicle(new Object());
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenVehicle_WhenLotIsNotEmpty_ShouldReturnFalse() {
        parkingLotSystem.parkVehicle(new Object());
        boolean isParked = parkingLotSystem.parkVehicle(new Object());
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        Object vehicle = new Object();
        boolean isParked = parkingLotSystem.parkVehicle(vehicle);
        boolean isUnParked = parkingLotSystem.unParkVehicle(vehicle);
    }
}
