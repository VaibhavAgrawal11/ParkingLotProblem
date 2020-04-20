package com.bridgelabz.utilities;

public class Vehicle {
    public String colour= "white";

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

    public Vehicle(String colour, Driver driver) {
        this.colour = colour;
        this.driver = driver;
    }
}
