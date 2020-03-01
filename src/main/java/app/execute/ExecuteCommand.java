package app.execute;

import app.constants.Constants;
import app.model.Car;
import app.service.ParkingService;
import app.service.ParkingServiceImpl;

public class ExecuteCommand {
    public static ParkingService parkingService = new ParkingServiceImpl();

    /**
     * Method will take string array of commands and execute
     * them
     * @param command
     */
    public void execute(String command) {
        {
            String[] inputs = command.split(" ");
            if (validate(inputs)) {
                switch (inputs[0]) {
                    case Constants.CREATE_PARKING_LOT:
                        // create a parking lot for given capacity
                        int capacity = Integer.parseInt(inputs[1]);
                        parkingService.createParkingLot(capacity);
                        break;
                    case Constants.PARK:
                        // park a car in the nearest available slot
                        parkingService.park(new Car(inputs[1], inputs[2]));
                        break;
                    case Constants.LEAVE:
                        // empty the parking slot
                        int slot = Integer.parseInt(inputs[1]);
                        parkingService.leave(slot);
                        break;
                    case Constants.STATUS:
                        // get current status of parking lot
                        parkingService.status();
                        break;
                    case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
                        parkingService.getCarParkedWithColor(inputs[1]);
                        break;
                    case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                        parkingService.getSlotOfCarWithColor(inputs[1]);
                        break;
                    case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
                        parkingService.getSlotForRegistrationNumber(inputs[1]);
                        break;
                    default:
                        System.out.println(Constants.INVALID);
                        break;
                }
            }
        }
    }

    /**
     * Will return true in case the command is valid
     *
     * @param inputs
     * @return
     */
    public Boolean validate(String[] inputs) {
        boolean valid = false;
        switch (inputs.length) {
            case 1:
                if (inputs[0].equals(Constants.STATUS)) {
                    valid = true;
                }
                break;
            case 2:
                if (inputs[0].equals(Constants.CREATE_PARKING_LOT)
                        || inputs[0].equals(Constants.LEAVE)
                        || inputs[0].equals(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR)
                        || inputs[0].equals(Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR)
                        || inputs[0].equals(Constants.SLOTS_NUMBER_FOR_REG_NUMBER))
                    valid = true;
                break;
            case 3:
                if (inputs[0].equals(Constants.PARK))
                    valid = true;
                break;
        }
        if (!valid) {
            System.out.println(Constants.INVALID);
        }
        return valid;
    }
}
