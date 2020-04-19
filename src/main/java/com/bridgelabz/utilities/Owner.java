package com.bridgelabz.utilities;

import com.bridgelabz.observer.Observer;

import java.util.HashMap;

public class Owner implements Observer {
    private HashMap<Integer, Object> parkingLotMap;

    public enum Sign {PARKING_IS_FULL}

    private Sign sign;

    public void sendParkingMessage(int currentVehicleCount, int lotCapacity) {
        if (currentVehicleCount >= lotCapacity)
            setSign(Sign.PARKING_IS_FULL);
        else setSign(null);
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public void getUpdatedMap(HashMap<Integer, Object> parkingLotMap) {
        this.parkingLotMap = parkingLotMap;
    }

    public Integer decideParkingSlot() {
        for (int i = 1; i <= parkingLotMap.size(); i++)
            if (parkingLotMap.get(i) == null) {
                return i;
            }
        return null;
    }
}
