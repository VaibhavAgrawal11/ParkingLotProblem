package com.bridgelabz.utilities;

public class ParkingAttendant {
    Owner owner = new Owner();
    public Integer parkingSlot;

    public Integer getParkingSlot() {
        return this.parkingSlot;
    }

    public void setParkingSlot(Integer parkingSlot) {
        parkingSlot = owner.decideParkingSlot();
        this.parkingSlot = parkingSlot;
    }
}
