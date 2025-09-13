package org.myproject.self.parkinglot;


import org.myproject.self.vehicles.Vehicle;

public class BikeParkingSpot extends ParkingSpot {

    public BikeParkingSpot(int spotNumber) {
        super(spotNumber, "Bike");
    }

    @Override
    public boolean canParkVehicle(Vehicle vehicle) {
        return "Bike".equalsIgnoreCase(vehicle.getVehicleType());
    }
}
