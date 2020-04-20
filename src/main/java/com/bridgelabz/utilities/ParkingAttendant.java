package com.bridgelabz.utilities;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingAttendant {
    static HashMap<Integer, HashMap> lotMaps;
    Owner owner = new Owner();
    static HashMap<Integer, Vehicle> currentMap = new HashMap<>();

    public Integer getMyParkingSlot(Vehicle vehicle) {
        Integer lotNumber = getLotNumber(vehicle);
        for (int i = 1; i <= this.lotMaps.get(lotNumber).size(); i++)
            if (this.lotMaps.get(lotNumber).get(i) == vehicle)
                return i;
        return null;
    }

    public Integer getLotNumber(Vehicle vehicle) {
        int count = 1;
        for (HashMap<Integer, Object> map : lotMaps.values()) {
            if (map.containsValue(vehicle))
                return count;
            count++;
        }
        return null;
    }

    public HashMap<Integer, HashMap> parkVehicle(Vehicle vehicle, HashMap<Integer, HashMap> lotMaps) {
        if (vehicle.driver.equals(Vehicle.Driver.HANDICAP))
            return parkAtNearestLocation(vehicle, lotMaps);
        this.lotMaps = lotMaps;
        Integer lotNumber = getCurrentMap(lotMaps);
        this.currentMap = lotMaps.get(lotNumber);
        owner.getUpdatedMap(this.currentMap);
        this.currentMap.put(owner.decideParkingSlot(), vehicle);
        this.lotMaps.put(lotNumber, this.currentMap);
        return ParkingAttendant.this.lotMaps;
    }

    private HashMap<Integer, HashMap> parkAtNearestLocation(Vehicle vehicle, HashMap<Integer, HashMap> lotMaps) {
        this.lotMaps = lotMaps;
        for (HashMap<Integer, Vehicle> map : this.lotMaps.values()) {
            if (map.containsValue(null)) {
                Integer counter =0;
                for (Vehicle slots : map.values()) {
                    counter++;
                    if (slots == null) {
                        map.put(counter,vehicle);
                        System.out.println(this.lotMaps);
                        return this.lotMaps;
                    }
                }
            }
        }
        return this.lotMaps;
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
