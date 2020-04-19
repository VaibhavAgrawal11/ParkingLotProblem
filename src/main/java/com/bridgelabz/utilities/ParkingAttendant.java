package com.bridgelabz.utilities;

import java.util.HashMap;

public class ParkingAttendant {
    static HashMap<Integer, Object> lotMap = new HashMap<Integer, Object>();
    Owner owner = new Owner();

    public Integer getMyParkingSlot(Object vehicle){
        for (int i = 1; i <=lotMap.size(); i++)
            if (lotMap.get(i) == vehicle)
                return i;
        return null;
    }

    public HashMap<Integer, Object> parkVehicle(Object vehicle, HashMap<Integer, Object> lotMap) {
        this.lotMap = lotMap;
        owner.getUpdatedMap(lotMap);
        lotMap.put(owner.decideParkingSlot(), vehicle);
        return lotMap;
    }
}
