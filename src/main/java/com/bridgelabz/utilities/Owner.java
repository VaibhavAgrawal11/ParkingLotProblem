package com.bridgelabz.utilities;

import com.bridgelabz.exception.ParkingLotException;

public class Owner {
    public void sendParkingFullMessage() throws ParkingLotException {
        throw new ParkingLotException("Parking lot is full.");
    }
}
