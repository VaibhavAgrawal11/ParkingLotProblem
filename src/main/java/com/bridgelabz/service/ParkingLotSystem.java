package com.bridgelabz.service;

public class ParkingLotSystem {
    Object vehicle = null;
    public boolean parkVehicle(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    public boolean unParkVehicle(Object o) {
        if(this.vehicle==null)
            return false;
        return true;
    }
}
