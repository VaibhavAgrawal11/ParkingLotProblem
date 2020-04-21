package com.bridgelabz.utilities;

import java.time.Duration;

public class ParkingBill {

    private static final double COST_PER_HOUR = 2.5;

    public static double generateParkingBill(Vehicle vehicle) {
        return (Duration.between(vehicle.arrivalTime, vehicle.departalTime).toHours()) * COST_PER_HOUR;
    }
}
