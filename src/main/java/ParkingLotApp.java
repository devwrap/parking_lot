import model.Car;
import service.ParkingService;
import service.ParkingServiceImpl;

import java.util.Scanner;

public class ParkingLotApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Parking lot application started: ");
        ParkingService parkingService = new ParkingServiceImpl();

        while(!"exit".equals(scanner.nextLine())) {
            String input = scanner.nextLine().trim();
            String[] arr = input.split(" ");
            if (arr.length > 1) {
                if (arr[0].equals("create")) {
                    // create a parking lot for given capacity
                    int capacity = Integer.parseInt(arr[1]);
                    parkingService.createParkingLot(capacity);
                }
                if (arr[0].equals("park")) {
                    // park a car in the nearest available slot
                    String number = arr[1];
                    String color = arr[2];
                    Car car = new Car(number, color);
                    parkingService.park(car);
                }
                if (arr[0].equals("leave")) {
                    // empty the parking slot
                    int slot = Integer.parseInt(arr[1]);
                    parkingService.leave(slot);
                }
                if (arr[0].equals("status")) {
                    // get current status of parking lot
                }

                //todo: create logic to get queries
            } else if (arr.length == 1) {
                // check if valid path to file is entered or not

                //if yes then read the file and continue the process
            } else if (arr.length == 0) {
                System.out.println("Enter exit to exit application..");
            }
        }
    }
}
