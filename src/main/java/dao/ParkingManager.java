package dao;

import model.Car;
import model.ParkingSlot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingManager {

    private int capacity;
    private int availability;
    private ParkingSlot parkingSlot;
    private Map<Integer, Car> slotCarMap;
    private static ParkingManager parkingManager = null;

    private ParkingManager(int capacity) {
        this.capacity = capacity;
        this.availability = capacity;
        this.parkingSlot = new ParkingSlot(capacity);
        slotCarMap = new ConcurrentHashMap<Integer, Car>();
        for (int i = 1; i <= capacity; i++) {
            slotCarMap.put(i, null);
        }
    }

    public static ParkingManager getInstance(int capacity) {
        if (parkingManager == null) {
            synchronized (ParkingManager.class) {
                if (parkingManager == null) {
                    return new ParkingManager(capacity);
                }
            }
        }
        return parkingManager;
    }

    public void park(Car car) {
        if(this.availability == 0) {
            System.out.println("No parking slots available");
            return;
        }
        // check for the slot availability in tree set
        int freeSlot = parkingSlot.getFreeSlot();
        if (slotCarMap.containsValue(car)) {
            System.out.println("Car already parked..");
        } else {
            slotCarMap.put(freeSlot, car);
            availability--;
            parkingSlot.removeSlot(freeSlot);
        }
    }

    public void leave(int slot) {
        if (availability == capacity) {
            System.out.println("No car parked yet..");
        }
        else if (slotCarMap.get(slot) == null) {
            System.out.println("No Car parked in this slot..");
        } else {
            slotCarMap.put(slot, null);
            availability++;
            parkingSlot.addSlot(slot);
        }
    }

    public void status() {
        if (availability == capacity) {
            System.out.println("Parking slots empty..");
        } else {
            System.out.println("Slot no. \t Registration No \t Colour");
            slotCarMap.entrySet().stream().forEach(entry -> {
                if (null != entry.getValue()) {
                    System.out.println(entry.getKey() + "\t" + entry.getValue().getNumber() + "\t" + entry.getValue().getColor());
                }
            });
        }
    }
}
