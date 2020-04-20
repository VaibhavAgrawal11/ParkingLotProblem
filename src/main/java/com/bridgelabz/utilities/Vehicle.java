package com.bridgelabz.utilities;

public class Vehicle {
    public enum Driver {NORMAL, HANDICAP}

    Driver driver;

    public Vehicle(Driver driver) {
        this.driver = driver;
    }
}
