package com.bridgelabz.utilities;

import java.util.ArrayList;

public class ParkingLot {
    private int lotCapacity=100;

    public int getLotCapacity() {
        return lotCapacity;
    }

    public void setLotCapacity(int lotCapacity) {
        this.lotCapacity = lotCapacity;
    }

    ArrayList parkingLot = new ArrayList();

    public ArrayList getParkingLots() {
        return parkingLot;
    }
}
