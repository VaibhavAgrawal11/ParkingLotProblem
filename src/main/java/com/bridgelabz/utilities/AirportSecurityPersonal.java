package com.bridgelabz.utilities;

import com.bridgelabz.observer.Observer;

public class AirportSecurityPersonal implements Observer {
    boolean parkingIsFull;

    @Override
    public void sendParkingMessage(int currentVehicleCount, int lotCapacity) {
        if (currentVehicleCount >= lotCapacity)
            parkingIsFull = true;
    }

    public boolean redirectSecurityStaff() {
        return parkingIsFull;
    }
}
