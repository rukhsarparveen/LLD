package ALL;
import java.time.LocalDateTime;
import java.util.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotSystemMain {
    public static void main(String[] args) {
        System.out.println("WELCOME TO PARKING SYTEM");
        List<ParkingSpot> spots = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            spots.add(new BikeParkingSpot("B-" + i,i));
        }
        for (int i = 0; i < 10; i++) {
            spots.add(new CarParkingSpot("C-" + i, i));
        }
        for (int i = 0; i < 5; i++) {
            spots.add(new TruckParkingSpot("T-" + i, i));
        }

        ParkingLot parkingLot = new ParkingLot("Star Parking", spots);

        Vehicle vehicle = new Car("Vehicle - a");
        ParkingService parkingService = new ParkingService(parkingLot, new NearestSpotStrategy(),new EmailNotifications());
        Ticket ticket = parkingService.parkingService(vehicle);
        if (ticket == null) {
            System.out.println("Car not Parked");
        } else {
            ExitService exitService = new ExitService(new CalculateHours(), new UPIPaymentStrategy(), new EmailNotifications());
            boolean exit = exitService.exitSevice(ticket);
        }

    }
}

class ParkingService {
    ParkingLot parkingLot;
    Notification notification;
    SpotAllocationStrategy spotAllocationStrategy;

    public ParkingService(ParkingLot parkingLot,  SpotAllocationStrategy spotAllocationStrategy, Notification notification) {
        this.parkingLot = parkingLot;
        this.spotAllocationStrategy = spotAllocationStrategy;
        this.notification = notification;
    }

    Ticket parkingService(Vehicle vehicle) {
        ParkingSpot parkingSpot = spotAllocationStrategy.allocate(parkingLot, vehicle);
        if (parkingSpot == null) {
            System.out.println("Parking Spot in not Available");
            return null;
        } else {
            parkingSpot.park();
            Ticket ticket = new Ticket("Ticket number 1", vehicle, parkingSpot);
            notification.notifyUser("Car has been Parked");
            return ticket;
        }
    }
}

class ExitService {
    CalculateHours calculateHours;
    PaymentStrategy paymentStrategy;
    Notification notification;

    public ExitService(CalculateHours calculateHours, PaymentStrategy paymentStrategy, Notification notification) {
        this.calculateHours = calculateHours;
        this.paymentStrategy = paymentStrategy;
        this.notification = notification;
    }

    boolean exitSevice(Ticket ticket) {
        if (ticket == null) {
            return false;
        } else {
            long hours = calculateHours.calculateHours(ticket);
            FeeCalculationStrategy feeCalculationStrategy = ticket.vehicle.getFeeCalculationStrategy();
            double amount = feeCalculationStrategy.calculate(hours);
            paymentStrategy.pay(amount);
            ticket.parkingSpot.unPark();
            notification.notifyUser("Car has been Unparked");
            return true;
        }
    }
}

interface Vehicle {
    String getVehicleId();

    FeeCalculationStrategy getFeeCalculationStrategy();
}

class Car implements Vehicle {
    String id;

    public Car(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return this.id;
    }

    public FeeCalculationStrategy getFeeCalculationStrategy() {
        return new CarFeeStrategy();
    }
}

class Bike implements Vehicle {
    String id;

    public Bike(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return this.id;
    }

    public FeeCalculationStrategy getFeeCalculationStrategy() {
        return new BikeFeeStrategy();
    }
}

class Truck implements Vehicle {
    String id;

    public Truck(String id) {
        this.id = id;
    }

    public String getVehicleId() {
        return this.id;
    }

    public FeeCalculationStrategy getFeeCalculationStrategy() {
        return new TruckFeeStrategy();
    }
}

interface FeeCalculationStrategy {
    double calculate(long hours);
}

class CarFeeStrategy implements FeeCalculationStrategy {
    public double calculate(long hours) {
        return hours * 10;
    }
}

class BikeFeeStrategy implements FeeCalculationStrategy {
    public double calculate(long hours) {
        return hours * 5;
    }
}

class TruckFeeStrategy implements FeeCalculationStrategy {
    public double calculate(long hours) {
        return hours * 20;
    }
}

class CalculateHours {
    long calculateHours(Ticket ticket) {
        LocalDateTime exitTime = LocalDateTime.now();
        Duration duration = Duration.between(ticket.entryTime, exitTime);
        long minutes = duration.toMinutes();
        long hours = (long) Math.ceil(minutes / 60);
        return hours;
    }
}

interface PaymentStrategy {
    void pay(double amount);
}

class UPIPaymentStrategy implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Payment is done using UPI: $" + amount);
    }
}

class CardPaymentStrategy implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Payment is done using CARD: $" + amount);
    }
}

interface Notification {
    void notifyUser(String msg);
}

class EmailNotifications implements Notification {
    public void notifyUser(String msg) {
        System.out.println(msg);
    }
}

class SMSNotifications implements Notification {
    public void notifyUser(String msg) {
        System.out.println(msg);
    }
}

interface ParkingSpot {
    String getParkingSpotId();
    boolean isAvailable();
    int getDistanceFromEntryGate();

    void park();

    void unPark();

    boolean canFit(Vehicle vehicle);
}

class BikeParkingSpot implements ParkingSpot {
    String parkingSpotId;
    boolean available;
    int distance;

    BikeParkingSpot(String parkingSpotId, int disance) {
        this.parkingSpotId = parkingSpotId;
        this.available = true;
        this.distance = disance;
    }

    @Override
    public String getParkingSpotId() {
        return this.parkingSpotId;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void park() {
        available = false;
    }

    @Override
    public void unPark() {
        available = true;
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return vehicle instanceof Bike;
    }

    @Override
    public int getDistanceFromEntryGate(){
        return this.distance;
    }
}

class CarParkingSpot implements ParkingSpot {
    String parkingSpotId;
    boolean available;
    int distance;

    CarParkingSpot(String parkingSpotId, int distance) {
        this.parkingSpotId = parkingSpotId;
        this.available = true;
        this.distance = distance;
    }

    @Override
    public String getParkingSpotId() {
        return this.parkingSpotId;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void park() {
        available = false;
    }

    @Override
    public void unPark() {
        available = true;
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return vehicle instanceof Car;
    }

    @Override
    public int getDistanceFromEntryGate(){
        return this.distance;
    }
}

class TruckParkingSpot implements ParkingSpot {
    String parkingSpotId;
    boolean available;
    int distance;

    TruckParkingSpot(String parkingSpotId, int distance) {
        this.parkingSpotId = parkingSpotId;
        this.available = true;
        this.distance = distance;
    }

    @Override
    public String getParkingSpotId() {
        return this.parkingSpotId;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void park() {
        available = false;
    }

    @Override
    public void unPark() {
        available = true;
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return vehicle instanceof Truck;
    }
    @Override
    public int getDistanceFromEntryGate(){
        return this.distance;
    }
}

class ParkingLot {
    private String name;
    private List<ParkingSpot> parkingSpots;

    public ParkingLot(String name, List<ParkingSpot> parkingSpots) {
        this.name = name;
        this.parkingSpots = parkingSpots;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}

interface SpotAllocationStrategy {
    ParkingSpot allocate(ParkingLot lot, Vehicle vehicle);
}

class FirstAvailableSpotStrategy implements SpotAllocationStrategy {

    public ParkingSpot allocate(ParkingLot lot, Vehicle vehicle) {
        for (ParkingSpot spot : lot.getParkingSpots()) {
            if (spot.isAvailable() && spot.canFit(vehicle)) {
                return spot;
            }
        }
        return null;
    }
}

class NearestSpotStrategy implements SpotAllocationStrategy {

    public ParkingSpot allocate(ParkingLot lot, Vehicle vehicle) {
        ParkingSpot nearestSpot = null;
        int minDistance = Integer.MAX_VALUE;

        for (ParkingSpot spot : lot.getParkingSpots()) {
            if (spot.isAvailable() && spot.canFit(vehicle)) {
                if (spot.getDistanceFromEntryGate() < minDistance) {
                    minDistance = spot.getDistanceFromEntryGate();
                    nearestSpot = spot;
                }
            }
        }
        return nearestSpot;
    }
}


class Ticket {
    String ticketNumber;
    Vehicle vehicle;
    ParkingSpot parkingSpot;
    LocalDateTime entryTime;

    public Ticket(String ticketNumber, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.ticketNumber = ticketNumber;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
    }
}