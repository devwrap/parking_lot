import app.model.Car;
import app.service.ParkingService;
import app.service.ParkingServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ParkingLotTests {

    private final ByteArrayOutputStream output	= new ByteArrayOutputStream();

    @Before
    public void init()
    {
//        parkingLevel = 1;
        System.setOut(new PrintStream(output));
    }

    @Test
    public void createParkLotTests() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(100);
        assertTrue("Created a parking lot with 100 slots".equalsIgnoreCase(output.toString().trim()));
        // check if it doesnt allow creation if parking lot is already created
        output.reset();
        parkingService.createParkingLot(200);
        assertTrue("Sorry, parking lot already created".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void parkCarTests() {
        ParkingService parkingService = new ParkingServiceImpl();
        Car car = new Car("abcd", "efgh");
        output.reset();
        parkingService.park(car);
        //should not allow to park a car before initiating parking lot
        assertTrue("No parking lot has not been created".equalsIgnoreCase(output.toString().trim()));
        parkingService.createParkingLot(10);
        output.reset();
        parkingService.park(car);
        assertTrue("Allocated slot number: 1".equalsIgnoreCase(output.toString().trim()));
        output.reset();
        //should not allow to park the same car again
        parkingService.park(car);
        assertTrue("Car already parked".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void parkingLotLimitTest() {
        ParkingService parkingService = new ParkingServiceImpl();
        Car car = new Car("abcd", "efgh");
        parkingService.createParkingLot(1);
        parkingService.park(car);
        output.reset();
        parkingService.park(new Car("123", "red"));
        assertTrue("Sorry, parking lot is full".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void leaveCarTest() {
        ParkingService parkingService = new ParkingServiceImpl();
        // leave car should not be allowed if parking lot is not created
        output.reset();
        parkingService.leave(1);
        assertTrue("No parking lot has not been created".equalsIgnoreCase(output.toString().trim()));
        parkingService.createParkingLot(10);
        parkingService.park(new Car("abc", "def"));
        output.reset();
        parkingService.leave(1);
        assertTrue("Slot number 1 is free".equalsIgnoreCase(output.toString().trim()));
        //leave slot for already empty slot
        output.reset();
        parkingService.leave(1);
        assertTrue("No Car parked in this slot".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void parkingLotStatusTest() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(1);
        parkingService.park(new Car("KA-01-HH-1234", "White"));
        output.reset();
        parkingService.status();
        String status = "Slot No.    Registration No     Color\n1           KA-01-HH-1234       White\n";
        assertTrue(status.equalsIgnoreCase(output.toString()));
    }

    @Test
    public void nearestParkingSlotAllotmentTest() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(3);
        parkingService.park(new Car("1","a"));
        parkingService.park(new Car("2","b"));
        parkingService.leave(1);
        parkingService.park(new Car("3","c"));
        output.reset();
        parkingService.status();
        String status = "Slot No.    Registration No     Color\n1           3                       c\n2           2                       b\n";
        assertTrue(status.equalsIgnoreCase(output.toString()));
    }

    @Test
    public void getRegistrationNumberWithColorTest() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(3);
        parkingService.park(new Car("1","a"));
        parkingService.park(new Car("2","a"));
        parkingService.park(new Car("3","b"));
        output.reset();
        parkingService.getCarParkedWithColor("a");
        assertTrue("1, 2".equalsIgnoreCase(output.toString().trim()));
        output.reset();
        parkingService.getCarParkedWithColor("z");
        assertTrue("No cars found".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void getSlotNumbersWithColor() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(3);
        parkingService.park(new Car("1","a"));
        parkingService.park(new Car("2","b"));
        parkingService.park(new Car("3","a"));
        output.reset();
        parkingService.getCarParkedWithColor("a");
        assertTrue("1, 3".equalsIgnoreCase(output.toString().trim()));
        output.reset();
        parkingService.getCarParkedWithColor("z");
        assertTrue("No cars found".equalsIgnoreCase(output.toString().trim()));
    }

    @Test
    public void getSlotNumberForCar() {
        ParkingService parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(3);
        parkingService.park(new Car("1","a"));
        parkingService.park(new Car("2","b"));
        parkingService.park(new Car("3","a"));
        output.reset();
        parkingService.getSlotForRegistrationNumber("1");
        assertTrue("1".equalsIgnoreCase(output.toString().trim()));
        output.reset();
        parkingService.getSlotForRegistrationNumber("19");
        assertTrue("No cars found".equalsIgnoreCase(output.toString().trim()));
    }
}
