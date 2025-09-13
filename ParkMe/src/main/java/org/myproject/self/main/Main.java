package org.myproject.self.main;

//import main.java.org.myproject.self.gates.EntranceGate;
//import main.java.org.myproject.self.gates.ExitGate;
//import main.java.org.myproject.self.parkinglot.ParkingFloor;
//import main.java.org.myproject.self.parkinglot.ParkingLot;
//import main.java.org.myproject.self.payment.PaymentService;

import org.myproject.self.gates.EntranceGate;
import org.myproject.self.gates.ExitGate;
import org.myproject.self.parkinglot.ParkingFloor;
import org.myproject.self.parkinglot.ParkingLot;
import org.myproject.self.payment.PaymentService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the parking lot with one floor and two spots for simplicity
        ParkingFloor floor = new ParkingFloor(1, 2, 2);  // 2 car spots and 2 bike spots
        List<ParkingFloor> floors = new ArrayList<>();
        floors.add(floor);
        ParkingLot parkingLot = new ParkingLot(floors);

        // Initialize PaymentService, passing the scanner to it
        PaymentService paymentService = new PaymentService(scanner);

        // Initialize gates, passing both ParkingLot and PaymentService to ExitGate
        EntranceGate entranceGate = new EntranceGate(parkingLot);
        ExitGate exitGate = new ExitGate(parkingLot, paymentService);

        // Start of the parking lot system
        System.out.println("\n=========================================");
        System.out.println("   Welcome to the Parking Lot System!   ");
        System.out.println("=========================================");

        // Continuous parking session until the user exits
        boolean exit = false;
        while (!exit) {
            // Display menu
            showMenu();

            // Get user choice
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    // Park a vehicle via the entrance gate
                    parkVehicle(entranceGate);
                    break;

                case 2:
                    // Vacate a parking spot via the exit gate
                    vacateSpot(exitGate, scanner);
                    break;

                case 3:
                    //Display the status of all parking spots
                    printParkingStatus(parkingLot);
                    break;

                case 4:
                    // Exit the session
                    exit = true;
                    System.out.println("Thank you for using the Parking Lot System!");
                    break;

                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }

        scanner.close(); // Always close the scanner to avoid resource leak
    }

    private static void showMenu() {
        System.out.println("\n******************************************************");
        System.out.println("Please choose an option from below:");
        System.out.println("1. Park a vehicle");
        System.out.println("2. Vacate a vehicle spot");
        System.out.println("3. Print parking status");
        System.out.println("4. Exit the system");
        System.out.println("******************************************************");
    }

    private static int getUserChoice(Scanner scanner) {
        return scanner.nextInt();
    }

    private static void parkVehicle(EntranceGate entranceGate) {
        entranceGate.processEntrance();
    }

    private static void vacateSpot(ExitGate exitGate, Scanner scanner) {
        try {
            System.out.println("Enter the spot number to vacate: ");
            int spotNumber = scanner.nextInt();
            System.out.println("Enter the number of hours the vehicle stayed: ");
            int hoursStayed = scanner.nextInt();
            exitGate.processExit(spotNumber, hoursStayed);
        }catch (InputMismatchException ex){
            System.out.println("Invalid Input !! Try again.");
        }catch (Exception ex){
            System.out.println("Exception occured while vacating vehicle : " + ex.getMessage());
        }
    }

    private static void printParkingStatus(ParkingLot parkingLot) {
        parkingLot.getDetails();
    }

}
