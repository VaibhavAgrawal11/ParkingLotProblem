package com.bridgelabz.utilities;

import com.bridgelabz.service.ParkingLotSystem;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PoliceDepartment {

    private final ParkingLotSystem parkingLotSystem;
    List colouredVehicle = new ArrayList();
    List<Vehicle> vehicleRecent = new ArrayList<Vehicle>();

    public PoliceDepartment(ParkingLotSystem parkingLotSystem) {
        this.parkingLotSystem = parkingLotSystem;
    }

    public List getColouredVehicleList(String colour) {
        List colouredVehicle = new ArrayList<String>();
        for (HashMap<Integer, Vehicle> vehicleHashMap : parkingLotSystem.lotMaps.values()) {
            while (vehicleHashMap.values().remove(null)) ;
            colouredVehicle = vehicleHashMap.entrySet()
                    .stream()
                    .filter(map -> colour.equals(map.getValue().colour))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            this.colouredVehicle.addAll(colouredVehicle);
        }
        return this.colouredVehicle;
    }

    public List getDetailsOfParticularTypeOfVehicle(String carCompany, String colour) {
        List<String> vehicleDetails = new ArrayList<>();
        int parkingLot = 1;
        for (HashMap<Integer, Vehicle> vehicleHashMap : parkingLotSystem.lotMaps.values()) {
            List vehicleList = new ArrayList<String>();
            while (vehicleHashMap.values().remove(null)) ;
            vehicleList = vehicleHashMap.entrySet()
                    .stream()
                    .filter(x -> {
                        return (colour.equals(x.getValue().colour) && carCompany.equals(x.getValue().carCompany));
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            for (int i = 0; i < vehicleList.size(); i++) {
                String vehicleDetail = "PN:" + vehicleHashMap.get(vehicleList.get(i)).plateNumber + "  " +
                        "valetName:" + vehicleHashMap.get(vehicleList.get(i)).attendantName + "  " +
                        "L:" + parkingLot + "  " +
                        vehicleList.get(i);
                vehicleDetails.add(vehicleDetail);
            }
            parkingLot++;
        }
        System.out.println(vehicleDetails);
        return vehicleDetails;
    }

    public List<String> getSecurityWhenGivenExpensiveCarCountIncreases(String carCompany) {
        List colouredVehicle = new ArrayList<String>();
        List<String> vehicleSlot = new ArrayList<>();
        int counter = 1;
        for (HashMap<Integer, Vehicle> vehicleHashMap : parkingLotSystem.lotMaps.values()) {
            while (vehicleHashMap.values().remove(null)) ;
            colouredVehicle = vehicleHashMap.entrySet()
                    .stream()
                    .filter(map -> carCompany.equals(map.getValue().carCompany))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (int i = 0; i < colouredVehicle.size(); i++) {
                String lot = counter + " " + colouredVehicle.get(i);
                vehicleSlot.add(lot);
            }
            counter++;
        }
        return vehicleSlot;
    }

    public List<Vehicle> getRecentVehicle(int currentHour, int currentMin) {
        LocalTime l2 = LocalTime.of(currentHour, currentMin);
        for (HashMap<Integer, Vehicle> parkingLotMap : parkingLotSystem.lotMaps.values()) {
            while (parkingLotMap.values().remove(null)) ;
            for (Integer k : parkingLotMap.keySet()) {
                LocalTime l1 = parkingLotMap.get(k).getParkTime();
                if (Duration.between(l1, l2).toMinutes() <= 30 && Duration.between(l1, l2).toMinutes() >= 0) {
                    vehicleRecent.add(parkingLotMap.get(k));
                }
            }
        }
        return vehicleRecent;
    }


}
