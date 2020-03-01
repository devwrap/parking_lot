package app;

import app.model.Car;
import app.service.ParkingService;
import app.service.ParkingServiceImpl;

import java.io.*;

public class ParkingLotApp {

    public static ParkingService parkingService = new ParkingServiceImpl();

    public static void main(String[] args) {
        BufferedReader bufferedReader;
//        System.out.println("Parking lot application started: ");
        // Command line inputs
        if (args.length == 0) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String command = bufferedReader.readLine().trim();
                while (!"exit".equals(command)) {
                    if (command.isEmpty()) {
                        System.out.println("Enter exit to end application..");
                    } else if (validateCommand(command)) {
                        executeCommand(command.split(" "));
                    }
                    command = bufferedReader.readLine().trim();
                }
            } catch (IOException e) {
                System.out.println("IO exception");
                e.printStackTrace();
            }
        }
        // Command inputs through file
        else if (args.length == 1) {
            // File read and implement
//            String currentDirectory = System.getProperty("user.dir");
            String fileDir = args[0];
            File file = new File(fileDir);
            String command = null;
            try {
                FileReader fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                while ((command = bufferedReader.readLine()) != null && !command.equals("exit")) {
                    command = command.trim();
                    if (!command.isEmpty() && validateCommand(command)) {
                        executeCommand(command.split(" "));
                    }
                }
            } catch (IOException e) {
                System.out.println("File not found!!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid input arguments..");
        }
    }

    public static Boolean validateCommand(String command) {
        String[] inputs = command.split(" ");
        boolean valid = false;
        switch (inputs.length) {
            case 1:
                if (inputs[0].equals("status")) {
                    valid = true;
                }
                break;
            case 2:
                if (inputs[0].equals("create_parking_lot")
                        || inputs[0].equals("leave")
                        || inputs[0].equals("registration_numbers_for_cars_with_colour")
                        || inputs[0].equals("slot_numbers_for_cars_with_colour")
                        || inputs[0].equals("slot_number_for_registration_number"))
                    valid = true;
                break;
            case 3:
                if (inputs[0].equals("park"))
                    valid = true;
                break;
        }
        if (!valid) {
            System.out.println("Invalid command!");
        }
        return valid;
    }

    public static void executeCommand(String[] inputs) {
        switch (inputs[0]) {
            case "create_parking_lot":
                // create a parking lot for given capacity
                int capacity = Integer.parseInt(inputs[1]);
                parkingService.createParkingLot(capacity);
                break;
            case "park":
                // park a car in the nearest available slot
                parkingService.park(new Car(inputs[1], inputs[2]));
                break;
            case "leave":
                // empty the parking slot
                int slot = Integer.parseInt(inputs[1]);
                parkingService.leave(slot);
                break;
            case "status":
                // get current status of parking lot
                parkingService.status();
                break;
            case "registration_numbers_for_cars_with_colour":
                parkingService.getCarParkedWithColor(inputs[1]);
                break;
            case "slot_numbers_for_cars_with_colour":
                parkingService.getSlotOfCarWithColor(inputs[1]);
                break;
            case "slot_number_for_registration_number":
                parkingService.getSlotForRegistrationNumber(inputs[1]);
                break;
            default:
                System.out.println("Invalid ");
                break;
        }

    }
}
