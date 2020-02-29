package model;

import java.util.TreeSet;

public class ParkingSlot {
    private TreeSet<Integer> availableSlots;

    public ParkingSlot(int capacity) {
        this.availableSlots = new TreeSet<Integer>();
        for(int i = 1; i <= capacity; i++)
            this.availableSlots.add(i);
    }

    public int getFreeSlot() {
        return this.availableSlots.first();
    }
    public void addSlot(int slotNumber) {
        this.availableSlots.add(slotNumber);
    }
    public void removeSlot(int slotNumber) {
        this.availableSlots.remove(slotNumber);
    }
}
