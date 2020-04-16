package com.bridgelabz.servicetest;

import com.bridgelabz.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class test {
    @Test
    public void givenVehicle_BeenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.parkVehicle(new Object());
        Assert.assertTrue(isParked);
    }
}
