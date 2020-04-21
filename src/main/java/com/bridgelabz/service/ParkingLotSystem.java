package com.bridgelabz.service;

import com.bridgelabz.exception.ParkingLotException;
import com.bridgelabz.observer.Observer;
import com.bridgelabz.observer.Subject;
import com.bridgelabz.utilities.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParkingLotSystem implements Subject {
    ArrayList<Observer> observers = new ArrayList<Observer>();
    ParkingAttendant attendant;
    int counter = 0;
    ParkingLot parkingLot;
    PoliceDepartment policeDepartment;
    int lotCapacity;
    int lotSize;
    public HashMap<Integer, HashMap> lotMaps = new HashMap<Integer, HashMap>();
    static LocalTime arrivalTime;
    static LocalTime departalTime;

    public ParkingLotSystem(int lotCapacity, int lotSize) {
        this.lotSize = lotSize;
        this.lotCapacity = lotCapacity;
        parkingLot = new ParkingLot(lotCapacity);
        for (int i = 1; i <= lotSize; i++) {
            HashMap<Integer, Vehicle> map = parkingLot.getEmptyParkingLot();
            lotMaps.put(i, map);
        }
        attendant = new ParkingAttendant();
        policeDepartment = new PoliceDepartment(this);
    }

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

    public void parkVehicle(Vehicle vehicle, int arrivingHour, int arrivingMin) throws ParkingLotException {
        if (counter >= lotCapacity * lotSize)
            throw new ParkingLotException("Parking lot is full.",
                    ParkingLotException.ExceptionType.NO_PARKING_AVAILABLE);
        AtomicBoolean vehicleCheck = new AtomicBoolean(false);
        lotMaps.values().stream().forEach(hashMap -> {
            if (hashMap.containsValue(vehicle)) {
                vehicleCheck.set(true);
            }
        });
        if (vehicleCheck.get())
            throw new ParkingLotException("Vehicle id already present",
                    ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT);
        lotMaps = attendant.parkVehicle(vehicle, lotMaps);
        counter++;
        arrivalTime = LocalTime.of(arrivingHour, arrivingMin);
        vehicle.arrivalTime = arrivalTime;
        this.notifyObservers();
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for (HashMap map : lotMaps.values()) {
            if (map.containsValue(vehicle))
                return true;
        }
        return false;
    }

    public boolean unParkVehicle(Vehicle vehicle, Integer parkingSlot, Integer parkingLotNumber, int departingHour, int departingMin) {
        if (lotMaps.get(parkingLotNumber).containsValue(vehicle)) {
            departalTime = LocalTime.of(departingHour, departingMin);
            vehicle.departalTime = departalTime;
            lotMaps.get(parkingLotNumber).put(parkingSlot, null);
            counter--;
            this.notifyObservers();
            return true;
        }
        return false;
    }
}