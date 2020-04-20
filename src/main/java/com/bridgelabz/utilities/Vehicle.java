package com.bridgelabz.utilities;

public class Vehicle {
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
}
