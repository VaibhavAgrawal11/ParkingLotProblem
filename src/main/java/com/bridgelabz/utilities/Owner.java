package com.bridgelabz.utilities;

import com.bridgelabz.observer.Observer;

public class Owner implements Observer {
    public enum Sign {PARKING_IS_FULL}

    private Sign sign;

    public void sendParkingMessage(int currentVehicleCount, int lotCapacity) {
        if (currentVehicleCount >= lotCapacity)
            setSign(Sign.PARKING_IS_FULL);
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }
}
