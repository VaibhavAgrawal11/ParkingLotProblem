package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.Observer;
import com.bridgelabz.observer.Subject;
import com.bridgelabz.utilities.ParkingAttendant;
import com.bridgelabz.utilities.ParkingBill;
import com.bridgelabz.utilities.ParkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ParkingLotSystem implements Subject {
    ArrayList<Observer> observers = new ArrayList<Observer>();
    ParkingAttendant attendant;
    ParkingBill parkingBill = new ParkingBill();
    int counter = 0;
    ParkingLot parkingLot;
    int lotCapacity;
    HashMap<Integer, Object> lotMap;

    public ParkingLotSystem(int lotCapacity) {
        this.lotCapacity = lotCapacity;
        parkingLot = new ParkingLot(lotCapacity);
        lotMap = parkingLot.getEmptyParkingLot();
        attendant = new ParkingAttendant();
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void notifyObservers() {

        for (Iterator<Observer> it =
             observers.iterator(); it.hasNext(); ) {
            Observer o = it.next();
            o.sendParkingMessage(counter, this.lotCapacity);
        }
    }

    public void parkVehicle(Object vehicle, int arrivingHour) throws ParkingLotException {
        if (counter >= lotCapacity)
            throw new ParkingLotException("Parking lot is full.",
                    ParkingLotException.ExceptionType.NO_PARKING_AVAILABLE);
        if (lotMap.containsValue(vehicle))
            throw new ParkingLotException("Vehicle already Parked",
                    ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT);
        lotMap = attendant.parkVehicle(vehicle, lotMap);
        counter++;
        parkingBill.arrivingHour(arrivingHour);
        this.notifyObservers();
    }

    public boolean isVehicleParked(Object vehicle) {
        return lotMap.containsValue(vehicle);
    }

    public boolean unParkVehicle(Object vehicle, Integer parkingSlot, int departingHour) {
        if (lotMap.containsValue(vehicle)) {
            lotMap.put(parkingSlot, null);
            counter--;
            parkingBill.departureHour(departingHour);
            this.notifyObservers();
            return true;
        }
        return false;
    }
}