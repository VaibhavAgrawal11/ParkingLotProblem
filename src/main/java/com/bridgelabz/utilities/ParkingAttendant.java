package com.bridgelabz.utilities;

import java.util.Collections;
import java.util.HashMap;

import static javax.swing.UIManager.put;

public class ParkingAttendant {
    public String attendantName;
    static HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> lotMaps = new HashMap<>();

    Owner owner = new Owner();

    public ParkingAttendant(String attendantName) {
        this.attendantName = attendantName;
    }

    public ParkingAttendant() {
    }

    public String getSlotNumber(Vehicle vehicle) {
        int lotNumber = 1;
        char rowNumber = 'A';
        for (HashMap<Character, HashMap<Integer, Vehicle>> maps : lotMaps.values()) {
            for (HashMap map : maps.values()) {
                if (map.containsValue(vehicle)) {
                    for (int slotNUmber = 1; slotNUmber <= map.size(); slotNUmber++)
                        if (map.get(slotNUmber) == vehicle)
                            return lotNumber + " " + rowNumber + " " + slotNUmber;
                }
                rowNumber++;
            }
            lotNumber++;
        }
        return null;
    }

    public HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>>
    parkVehicle(Vehicle vehicle, HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> lotMaps) {
        System.out.println(lotMaps);
        System.out.println(lotMaps.get(1).get('A').get(1));

        if (vehicle.driver.equals(Vehicle.Driver.HANDICAP))
            return parkAtNearestLocation(vehicle, lotMaps);
        String[] getLotAndRow = getCurrentMap(lotMaps).split(" ");
        Integer lotNumber = Integer.valueOf(getLotAndRow[0]);

        HashMap<Character, HashMap<Integer, Vehicle>> rowMaps = lotMaps.get(lotNumber);
        Character rowChar = getLotAndRow[1].toCharArray()[0];



        HashMap<Character, HashMap<Integer, Vehicle>> mapHashMap = lotMaps.get(lotNumber);

        HashMap<Integer, Vehicle> mapRow = mapHashMap.get(rowChar);
        owner.getUpdatedMap(mapRow);
        mapRow.put(owner.decideParkingSlot(), vehicle);
        return lotMaps;
    }

    private HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> parkAtNearestLocation(Vehicle vehicle, HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> lotMaps) {
        this.lotMaps = lotMaps;
        for (HashMap<Character, HashMap<Integer, Vehicle>> maps : this.lotMaps.values()) {
            for (HashMap<Integer, Vehicle> map : maps.values())
                if (map.containsValue(null)) {
                    Integer counter = 0;
                    for (Vehicle slots : map.values()) {
                        counter++;
                        if (slots == null) {
                            map.put(counter, vehicle);
                            System.out.println(this.lotMaps);
                            return this.lotMaps;
                        }
                    }
                }
        }
        return this.lotMaps;
    }

    private String getCurrentMap(HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> lotMap) {
        int maxValue = 0;
        Integer lotNumber = 0;
        Character rowChar = 'A' - 1;
        for (HashMap<Character, HashMap<Integer, Vehicle>> maps : lotMap.values()) {
            rowChar = 'A' - 1;
            lotNumber++;
            for (HashMap<Integer, Vehicle> map : maps.values()) {
                int count = Collections.frequency(map.values(), null);
                if (count >= maxValue) {
                    rowChar++;
                    maxValue = count;
                }
            }
        }
        System.out.println(lotNumber + " " + rowChar);
        return lotNumber + " " + rowChar;
    }
}
