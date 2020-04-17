package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.utilities.Owner;
import com.bridgelabz.utilities.ParkingLot;

import java.util.ArrayList;

public class ParkingLotSystem {
    public ParkingLotSystem(int lotCapacity) {
        parkingLot.setLotCapacity(lotCapacity);
    }

    int counter = 0;
    Owner owner = new Owner();
    ParkingLot parkingLot = new ParkingLot();
    ArrayList lotList = parkingLot.getParkingLots();

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (counter >= parkingLot.getLotCapacity())
            owner.sendParkingFullMessage();
        lotList.add(vehicle);
        counter++;
    }

    public boolean isVehicleParked(Object vehicle) {
        return lotList.contains(vehicle);
    }

    public boolean unParkVehicle(Object vehicle) {
        if (lotList.contains(vehicle)) {
            lotList.remove(vehicle);
            counter--;
            return true;
        }
        return false;
    }


}
