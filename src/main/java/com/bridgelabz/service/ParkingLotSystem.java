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
    private int rowCapacity;
    ArrayList<Observer> observers = new ArrayList<Observer>();
    ParkingAttendant attendant;
    int counter = 0;
    ParkingLot parkingLot;
    PoliceDepartment policeDepartment;
    int lotCapacity;
    int lotSize;
    public HashMap<Integer, HashMap<Character, HashMap<Integer, Vehicle>>> lotMaps = new HashMap<>();
    static LocalTime arrivalTime;
    static LocalTime departTime;

    public ParkingLotSystem(int lotCapacity, int rowCapacity, int lotSize) {
        this.lotSize = lotSize;
        this.lotCapacity = lotCapacity;
        this.rowCapacity = rowCapacity;
        parkingLot = new ParkingLot(lotCapacity, rowCapacity);
        for (int i = 1; i <= lotSize; i++) {
            HashMap<Character, HashMap<Integer, Vehicle>> map = parkingLot.getEmptyParkingLot();
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
        for (Iterator<Observer>
             it =
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
        for (HashMap<Character, HashMap<Integer, Vehicle>> maps : lotMaps.values()) {
            maps.values().stream().forEach(hashMap -> {
                if (hashMap.containsValue(vehicle)) {
                    vehicleCheck.set(true);
                }
            });
        }
        if (vehicleCheck.get())
            throw new ParkingLotException("Vehicle id already present",
                    ParkingLotException.ExceptionType.VEHICLE_ALREADY_PRESENT);
        lotMaps = attendant.parkVehicle(vehicle, lotMaps);
        counter++;
        arrivalTime = LocalTime.of(arrivingHour, arrivingMin);
        vehicle.arrivalTime = arrivalTime;
        this.notifyObservers();
        return;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for (HashMap<Character, HashMap<Integer, Vehicle>> maps : lotMaps.values()) {
            for (HashMap<Integer, Vehicle> map : maps.values())
                if (map.containsValue(vehicle))
                    return true;
        }
        return false;
    }

    public boolean unParkVehicle(Vehicle vehicle, String parkingSlot, int departingHour, int departingMin) {
        String[] parkingDetail = parkingSlot.split(" ");
        Integer lotNumber = Integer.valueOf(parkingDetail[0]);
        Character rowChar = parkingDetail[1].toCharArray()[0];
        Integer slotNumber = Integer.valueOf(parkingDetail[2]);
        if (lotMaps.get(lotNumber).get(rowChar).containsValue(vehicle)) {
            departTime = LocalTime.of(departingHour, departingMin);
            vehicle.departalTime = departTime;
            lotMaps.get(lotNumber).get(rowChar).put(slotNumber, null);
            counter--;
            this.notifyObservers();
            return true;
        }
        return false;
    }
}