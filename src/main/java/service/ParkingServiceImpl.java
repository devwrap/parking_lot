package service;

import dao.ParkingManager;
import model.Car;

public class ParkingServiceImpl implements ParkingService {
    private ParkingManager parkingManager = null;

    public void createParkingLot(int capacity) {
        if (parkingManager != null) {
            System.out.println("parking lot already created!!");
            return;
        } else {
            parkingManager = ParkingManager.getInstance(capacity);
            System.out.println("Created Parking lot of capacity: " + capacity);
        }
    }

    public void park(Car car) {
        if (checkParkingCreation()) {
            parkingManager.park(car);
        }
    }

    public void leave(int slot) {
        if (checkParkingCreation()) {
            parkingManager.leave(slot);
        }
    }

    public void status() {
        if (checkParkingCreation()) {
           parkingManager.status();
        }
    }

    Boolean checkParkingCreation() {
        if (parkingManager == null) {
            System.out.println("parking lot has not been created!!");
            return false;
        }
        return true;
    }
}
