package com.bridgelabz.utilities;

public class ParkingSlot {
    private int row;
    private int column;
    private Vehicle vehicle;

    public void park(int i, int j, Vehicle vehicle) {
        this.row = i;
        this.column = j;
        this.vehicle = vehicle;
    }
}
