package service;

import model.Car;

public interface ParkingService {
    void createParkingLot(int capacity);
    void park(Car car);
    void leave(int slot);
    void status();
//    public void park()

}
