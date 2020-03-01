package app;

import app.constants.Constants;
import app.execute.ExecuteCommand;

import java.io.*;

public class ParkingLotApp {
    public static void main(String[] args) {
        BufferedReader bufferedReader;
        ExecuteCommand executeCommand = new ExecuteCommand();
        String command;
        // Command line inputs
        if (args.length == 0) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while (((command = bufferedReader.readLine()) != null) && !Constants.EXIT.equals(command)) {
                    if (!command.isEmpty()) {
                        executeCommand.execute(command);
                    } else {
                        System.out.println("Enter exit to end application..");
                    }
                }
            } catch (IOException e) {
                System.out.println("IO exception");
                e.printStackTrace();
            }
        }
        // Command inputs through file
        else if (args.length == 1) {
            // File read and implement
            String fileDir = args[0];
            File file = new File(fileDir);
            try {
                FileReader fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                while ((command = bufferedReader.readLine()) != null && !command.equals(Constants.EXIT)) {
                    if (!command.isEmpty()) {
                        executeCommand.execute(command.trim());
                    }
                }
            } catch (IOException e) {
                System.out.println("File not found!!");
                e.printStackTrace();
            }
        } else {
            System.out.println(Constants.INVALID);
        }
    }
}
