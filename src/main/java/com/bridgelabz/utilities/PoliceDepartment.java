package com.bridgelabz.utilities;

import com.bridgelabz.service.ParkingLotSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoliceDepartment {

    private final ParkingLotSystem parkingLotSystem;

    public PoliceDepartment(ParkingLotSystem parkingLotSystem) {
        this.parkingLotSystem = parkingLotSystem;
    }

    public List getColouredVehicleList(String colour) {
        List colouredVehicle = new ArrayList<String>();
        for (HashMap<Integer, Vehicle> vehicleHashMap : parkingLotSystem.lotMaps.values()) {
            while (vehicleHashMap.values().remove(null));
            colouredVehicle = vehicleHashMap.entrySet()
                    .stream()
                    .filter(map -> colour.equals(map.getValue().colour))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
        return colouredVehicle;
    }
}
