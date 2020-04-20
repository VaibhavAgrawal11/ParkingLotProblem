package com.bridgelabz.utilities;

import java.util.HashMap;

public class ParkingLot {
    public int lotCapacity;

    HashMap<Integer, Vehicle> parkingLotMap;

    public ParkingLot(int lotCapacity) {
        this.lotCapacity = lotCapacity;
    }

    public HashMap<Integer, Vehicle> getEmptyParkingLot() {
        parkingLotMap = new HashMap<>();
        for (int i = 1; i <= lotCapacity; i++) {
            parkingLotMap.put(i, null);
        }
        return parkingLotMap;
    }

}
