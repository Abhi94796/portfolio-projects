package org.myproject.self.parkinglot;

import org.myproject.self.vehicles.Vehicle;

import java.time.LocalDateTime;

public class Ticket {
    private ParkingSpot parkingSpot;
    private Vehicle vehicle;
    private LocalDateTime startTime;


    // Constructor to initialize ticket with parking spot and vehicle
    public Ticket(ParkingSpot parkingSpot, Vehicle vehicle) {
        this.parkingSpot = parkingSpot;
        this.vehicle = vehicle;
        this.startTime = LocalDateTime.now();  // Set current time as the start time
    }

}
