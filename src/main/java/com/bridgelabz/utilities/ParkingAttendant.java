package com.bridgelabz.utilities;

import java.util.HashMap;

public class ParkingAttendant {
    HashMap<Integer, Object> lotMap;
    Owner owner = new Owner();

    public HashMap<Integer, Object> parkVehicle(Object vehicle, HashMap<Integer, Object> lotMap) {
        this.lotMap = lotMap;
        owner.getUpdatedMap(lotMap);
        lotMap.put(owner.decideParkingSlot(), vehicle);
        return lotMap;
    }
}
