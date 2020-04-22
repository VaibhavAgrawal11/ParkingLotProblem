package com.bridgelabz.utilities;

import java.util.HashMap;

public class ParkingLot {
    public int lotCapacity;
    public int rowCapacity;
    int rowNumbs;
    HashMap<Integer, Vehicle> row = new HashMap<>();
    HashMap<Character, HashMap<Integer, Vehicle>> rowMap = new HashMap<>();

    public ParkingLot(int lotCapacity, int rowCapacity) {
        this.lotCapacity = lotCapacity;
        this.rowCapacity = rowCapacity;
        this.rowNumbs = lotCapacity / rowCapacity;
    }

    public HashMap<Character, HashMap<Integer, Vehicle>> getEmptyParkingLot() {
        Character character = 'A';
        for (int i = 1; i <= rowNumbs; i++) {
            for (int j = 1; j <= rowCapacity; j++) {
                row.put(j, null);
            }
            rowMap.put(character, row);
            character++;
        }
        return rowMap;
    }
}