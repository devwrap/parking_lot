package app.service;

import app.dao.ParkingManager;
import app.model.Car;

import java.util.List;

public class ParkingServiceImpl implements ParkingService {
    private ParkingManager<Car> parkingManager = null;

    public void createParkingLot(int capacity) {
        if (parkingManager != null) {
            System.out.println("parking lot already created!!");
        } else {
            this.parkingManager = ParkingManager.getInstance(capacity);
            System.out.println("Created a parking lot with " + capacity + "slots");
        }
    }

    public void park(Car car) {
        if (checkParkingCreation() && this.parkingManager.park(car)) {
            System.out.println("Car parked: " + car.getNumber());
        }
    }

    public void leave(int slot) {
        if (checkParkingCreation() && parkingManager.leave(slot)) {
            System.out.println("Slot: " + slot + " is available now");
        }
    }

    public void status() {
        if (checkParkingCreation()) {
           parkingManager.status();
        }
    }

    @Override
    public void getCarParkedWithColor(String color) {
        if (checkParkingCreation()) {
            List<String> carListWithColor = parkingManager.getCarParkedWithColor(color);
            if(carListWithColor.size() != 0) {
                System.out.println(String.join(",", carListWithColor));
            } else {
                System.out.println("No cars found");
            }
        }
    }

    @Override
    public void getSlotOfCarWithColor(String color) {
        if (checkParkingCreation()) {
            List<Integer> slots = parkingManager.getSlotOfCarWithColor(color);
            if (slots.size() > 0) {
                slots.forEach(slot -> System.out.print(slot + ", "));
            } else {
                System.out.println("No car of given color is parked");
            }
        }
    }

    @Override
    public void getSlotForRegistrationNumber(String carNumber) {
        if (checkParkingCreation()) {
            int slot = parkingManager.getSlotForRegistrationNumber(carNumber);
            if (slot != -1) {
                System.out.println(slot);
            } else {
                System.out.println("No car of given color is parked");
            }
        }
    }

    Boolean checkParkingCreation() {
        if (this.parkingManager == null) {
            System.out.println("parking lot has not been created!!");
            return false;
        }
        return true;
    }
}
