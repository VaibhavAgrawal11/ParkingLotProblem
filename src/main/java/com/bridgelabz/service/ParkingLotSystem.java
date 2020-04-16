package com.bridgelabz.service;

public class ParkingLotSystem {
    Object vehicle = null;
    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if(this.vehicle!=null) {
            throw new ParkingLotException("Parking lot is full.");
        }
        this.vehicle = vehicle;

    }

    public boolean unParkVehicle(Object vehicle) {
        if (this.vehicle==null) return false;
        if(this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }
}
