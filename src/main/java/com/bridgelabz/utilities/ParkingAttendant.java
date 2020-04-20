package com.bridgelabz.utilities;

import java.util.Collections;
import java.util.HashMap;

public class ParkingAttendant {
    static HashMap<Integer, HashMap> lotMaps;
    Owner owner = new Owner();
    static HashMap<Integer, Object> currentMap = new HashMap<>();

    public Integer getMyParkingSlot(Object vehicle) {
        for (HashMap map : this.lotMaps.values()) {
            for (int i = 1; i <= map.size(); i++)
                if (map.get(i) == vehicle)
                    return i;
        }
        return null;
    }

    public Integer getLotNumber(Object vehicle) {
        int count = 1;
        for (HashMap<Integer, Object> map : lotMaps.values()) {
            if (map.containsValue(vehicle))
                return count;
            count++;
        }
        return null;
    }

    public HashMap<Integer, HashMap> parkVehicle(Object vehicle, HashMap<Integer, HashMap> lotMaps) {
        this.lotMaps = lotMaps;
        Integer lotNumber = getCurrentMap(lotMaps);
        this.currentMap = lotMaps.get(lotNumber);
        owner.getUpdatedMap(this.currentMap);
        this.currentMap.put(owner.decideParkingSlot(), vehicle);
        this.lotMaps.put(lotNumber, this.currentMap);
        return ParkingAttendant.this.lotMaps;
    }

    private Integer getCurrentMap(HashMap<Integer, HashMap> lotMap) {
        int maxValue = 0;
        Integer lotNumber = 0;
        for (HashMap<Integer, Object> map : lotMap.values()) {
            int count = Collections.frequency(map.values(), null);
            if (count >= maxValue) {
                maxValue = count;
                lotNumber++;
            }
        }
        return lotNumber;
    }
}
