package com.bridgelabz.utilities;

public class Vehicle {
    public String colour = "white";
    public String plateNumber = "XX00 XX 0000";
    public String carCompany = "Toyota";
    public String attendantName;

    public enum Driver {NORMAL, HANDICAP}

    public enum VehicleType {MEDIUM, LARGE}

    Driver driver = Driver.NORMAL;
    VehicleType type = VehicleType.MEDIUM;

    public Vehicle(Driver driver) {
        this.driver = driver;
    }

    public Vehicle(VehicleType type) {
        this.type = type;
    }

    public Vehicle(String colour) {
        this.colour = colour;
    }

    public Vehicle(String colour, String plateNumber, String carCompany, String attendantName) {
        this.colour = colour;
        this.plateNumber = plateNumber;
        this.carCompany = carCompany;
        this.attendantName = attendantName;
    }
}
