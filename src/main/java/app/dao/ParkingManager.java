package app.dao;

import app.model.Car;
import app.model.ParkingSlot;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ParkingManager<T extends Car> {

    private final int capacity;
    private int availability;
    private ParkingSlot parkingSlot;
    private Map<Integer, Optional<T>> slotCarMap;
    private static ParkingManager parkingManager = null;

    private ParkingManager(int capacity) {
        this.capacity = capacity;
        this.availability = capacity;
        this.parkingSlot = new ParkingSlot(capacity);
        slotCarMap = new ConcurrentHashMap<>();
        for (int i = 1; i <= capacity; i++) {
            slotCarMap.put(i, Optional.empty());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Car> ParkingManager<T> getInstance(int capacity) {
        if (parkingManager == null) {
            synchronized (ParkingManager.class) {
                if (parkingManager == null) {
                    return new ParkingManager<T>(capacity);
                }
            }
        }
        return parkingManager;
    }

    public Boolean park(T car) {
        if (this.availability == 0) {
            System.out.println("Sorry, parking lot is full");
            return false;
        }
        // check for the slot availability in tree set
        int freeSlot = parkingSlot.getFreeSlot();

        for (int i = 1; i <= capacity; i++) {
            if (slotCarMap.get(i).isPresent() && Optional.of(car).get().equals(slotCarMap.get(i).get())) {
                System.out.println("Car already parked");
                return false;
            }
        }
        slotCarMap.put(freeSlot, Optional.of(car));
        availability--;
        parkingSlot.removeSlot(freeSlot);
        System.out.println("Allocated slot number: " + freeSlot);
        return true;
    }

    public boolean leave(int slot) {
        if (!slotCarMap.get(slot).isPresent()) {
            System.out.println("No Car parked in this slot");
            return false;
        } else {
            slotCarMap.put(slot, Optional.empty());
            availability++;
            parkingSlot.addSlot(slot);
            System.out.println("Slot number " + slot + " is free");
            return true;
        }
    }

    public void status() {
        if (availability == capacity) {
            System.out.println("Parking slots empty..");
        } else {
            StringBuilder sb = new StringBuilder();
//            sb.append("Slot No.    Registration No    Colour\n");
//            System.out.println("Slot No.\tRegistration No.\tColor");
            sb.append(String.format("%-12s", "Slot No."));
            sb.append(String.format("%-19s", "Registration No"));
            sb.append(String.format("%-6s", "Colour"));
            sb.append("\n");
            slotCarMap.forEach((key, value) -> {
                value.ifPresent(t -> {
                    sb.append(String.format("%-12s", key));
                    sb.append(String.format("%-19s", t.getNumber()));
                    sb.append(t.getColor());
                    sb.append("\n");
                });
            });
            System.out.print(sb);
        }
    }

    public List<String> getCarParkedWithColor(String color) {
        return slotCarMap.values().stream()
                .filter(value -> value.isPresent() && value.get().getColor().equals(color))
                .map(tOptional -> tOptional.get().getNumber())
                .collect(Collectors.toList());
    }

    public List<Integer> getSlotOfCarWithColor(String color) {
        return slotCarMap.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .filter(entry -> entry.getValue().get().getColor().equals(color))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int getSlotForRegistrationNumber(String carNum) {
        return slotCarMap.entrySet().stream()
                .filter(entry -> entry.getValue().isPresent())
                .filter(entry -> entry.getValue().get().getNumber().equals(carNum))
                .map(Map.Entry::getKey)
                .findAny()
                .orElse(-1);
    }
}
