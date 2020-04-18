package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.Observer;
import com.bridgelabz.observer.Subject;
import com.bridgelabz.utilities.Owner;
import com.bridgelabz.utilities.ParkingLot;

import java.util.ArrayList;
import java.util.Iterator;

public class ParkingLotSystem implements Subject {
    ArrayList<Observer> observers = new ArrayList<Observer>();
    int counter = 0;
    ParkingLot parkingLot;
    int lotCapacity;
    ArrayList lotList = new ArrayList();

    public ParkingLotSystem(int lotCapacity) {
        this.lotCapacity = lotCapacity;
        parkingLot = new ParkingLot(lotCapacity);
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(observers.indexOf(obj));
    }

    @Override
    public void notifyObservers() {

        for (Iterator<Observer> it =
             observers.iterator(); it.hasNext(); ) {
            Observer o = it.next();
            o.sendParkingMessage(counter, this.lotCapacity);
        }
    }

    public void parkVehicle(Object vehicle) throws ParkingLotException {
        if (counter >= lotCapacity)
            throw new ParkingLotException("Parking lot is full.");
        lotList.add(vehicle);
        counter++;
        this.notifyObservers();
    }

    public boolean isVehicleParked(Object vehicle) {
        return lotList.contains(vehicle);
    }

    public boolean unParkVehicle(Object vehicle) {
        if (lotList.contains(vehicle)) {
            lotList.remove(vehicle);
            counter--;
            return true;
        }
        return false;
    }
}
