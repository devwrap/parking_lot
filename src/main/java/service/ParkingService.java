package service;

import model.Car;

public interface ParkingService {
    void createParkingLot(int capacity);
    void park(Car car);
    void leave(int slot);
    void status();
    void getCarParkedWithColor(String color);
    void getSlotOfCarWithColor(String color);
    void getSlotForRegistrationNumber(String carNumber);
//    public void park()

}
