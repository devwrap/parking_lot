package app.service;

import app.dao.ParkingManager;
import app.model.Car;

import java.util.List;

public class ParkingServiceImpl implements ParkingService {
    private ParkingManager<Car> parkingManager = null;

    public void createParkingLot(int capacity) {
        if (parkingManager != null) {
            System.out.println("Sorry, parking lot already created");
        } else {
            this.parkingManager = ParkingManager.getInstance(capacity);
            System.out.println("Created a parking lot with " + capacity + " slots");
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

    @Override
    public void getCarParkedWithColor(String color) {
        if (checkParkingCreation()) {
            List<String> carListWithColor = parkingManager.getCarParkedWithColor(color);
            if(carListWithColor.size() != 0) {
                System.out.println(String.join(", ", carListWithColor));
            } else {
                System.out.println("Not found");
            }
        }
    }

    @Override
    public void getSlotOfCarWithColor(String color) {
        if (checkParkingCreation()) {
            List<Integer> slots = parkingManager.getSlotOfCarWithColor(color);
            if (slots.size() > 0) {

//                slots.forEach(slot -> {
//
//                    System.out.print(slot + ", ");
//                });
                StringBuilder sb = new StringBuilder();
                int count = 0;
                for(int slot : slots) {
                    if(count == slots.size()-1) {
                        sb.append(slot);
                    } else {
                        sb.append(slot).append(", ");
                    }
                    count ++;
                }
                System.out.println(sb);
            }
            else {
                    System.out.println("Not found");
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
                System.out.println("Not found");
            }
        }
    }

    Boolean checkParkingCreation() {
        if (this.parkingManager == null) {
            System.out.println("No parking lot has not been created");
            return false;
        }
        return true;
    }
}
