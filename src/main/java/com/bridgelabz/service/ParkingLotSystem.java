package com.bridgelabz.service;

public class ParkingLotSystem {
    Object vehicle = null;
    public boolean parkVehicle(Object vehicle) {
        if(this.vehicle==null) {
            this.vehicle = vehicle;
            return true;
        }
        return false;
    }

    public boolean unParkVehicle(Object vehicle) {
        if(this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
