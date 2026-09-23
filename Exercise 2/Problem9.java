package com.mycompany.problem9;

import java.util.Scanner;

public class Problem9 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of parking slots: ");
        int numberOfSlots = input.nextInt();

        ParkingSlot[] slots = new ParkingSlot[numberOfSlots];

        for (int i = 0; i < numberOfSlots; i++) {
            System.out.println("\nSlot " + (i + 1));

            System.out.print("Enter slot number: ");
            int slotNumber = input.nextInt();

            System.out.print("Enter slot type (M or C): ");
            String slotType = input.next();

            slots[i] = new ParkingSlot(slotNumber, slotType);
        }

        Garage garage = new Garage(slots);

        System.out.print("\nEnter number of operations: ");
        int operations = input.nextInt();

        for (int i = 1; i <= operations; i++) {
            System.out.println("\nOperation " + i);
            System.out.print("Enter operation (P for Park, E for Exit): ");
            String operation = input.next();

            if (operation.equalsIgnoreCase("P")) {
                System.out.print("Enter plate number: ");
                String plate = input.next();

                System.out.print("Enter owner name: ");
                String owner = input.next();

                System.out.print("Enter vehicle type (MOTORCYCLE or CAR): ");
                String type = input.next();

                Vehicle vehicle = new Vehicle(plate, owner, type);
                garage.park(vehicle);

            } else if (operation.equalsIgnoreCase("E")) {
                System.out.print("Enter plate number: ");
                String plate = input.next();

                System.out.print("Enter number of hours: ");
                int hours = input.nextInt();

                garage.exit(plate, hours);

            } else {
                System.out.println("Invalid operation.");
            }
        }

        System.out.println("\n=== FINAL GARAGE STATUS ===");
        System.out.println("Final Occupancy: " + Garage.getVehicleCount());
        garage.displayOccupiedSlots();

        input.close();
    }
}

class Vehicle {

    private String plateNumber;
    private String ownerName;
    private String type;

    public Vehicle(String plateNumber, String ownerName, String type) {
        this.plateNumber = plateNumber;
        this.ownerName = ownerName;
        this.type = type;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getType() {
        return type;
    }
}

class ParkingSlot {

    private int slotNumber;
    private String slotType;
    private boolean occupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotNumber, String slotType) {
        this.slotNumber = slotNumber;
        this.slotType = slotType;
        occupied = false;
        parkedVehicle = null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getSlotType() {
        return slotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    public void parkVehicle(Vehicle vehicle) {
        parkedVehicle = vehicle;
        occupied = true;
    }

    public void removeVehicle() {
        parkedVehicle = null;
        occupied = false;
    }
}

class Garage {

    private ParkingSlot[] slots;
    private static int vehicleCount = 0;

    public Garage(ParkingSlot[] slots) {
        this.slots = slots;
    }

    public static int getVehicleCount() {
        return vehicleCount;
    }

    public void park(Vehicle vehicle) {
        if (isDuplicatePlate(vehicle.getPlateNumber())) {
            System.out.println("Parking rejected: Duplicate plate already parked.");
            return;
        }

        for (int i = 0; i < slots.length; i++) {
            if (!slots[i].isOccupied() && isCompatible(slots[i], vehicle)) {
                slots[i].parkVehicle(vehicle);
                vehicleCount++;

                System.out.println("Vehicle " + vehicle.getPlateNumber()
                        + " parked in slot " + slots[i].getSlotNumber() + ".");
                return;
            }
        }

        System.out.println("Parking rejected: No compatible free slot.");
    }

    public boolean isDuplicatePlate(String plateNumber) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied()) {
                if (slots[i].getParkedVehicle().getPlateNumber()
                        .equalsIgnoreCase(plateNumber)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCompatible(ParkingSlot slot, Vehicle vehicle) {
        if (vehicle.getType().equalsIgnoreCase("MOTORCYCLE")) {
            return slot.getSlotType().equalsIgnoreCase("M")
                    || slot.getSlotType().equalsIgnoreCase("C");
        }

        if (vehicle.getType().equalsIgnoreCase("CAR")) {
            return slot.getSlotType().equalsIgnoreCase("C");
        }

        return false;
    }

    public void exit(String plateNumber, int hours) {
        if (hours < 1) {
            System.out.println("Exit rejected: Hours must be at least 1.");
            return;
        }

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied()) {
                Vehicle vehicle = slots[i].getParkedVehicle();

                if (vehicle.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                    double fee = calculateFee(vehicle, hours);

                    slots[i].removeVehicle();
                    vehicleCount--;

                    System.out.printf("Vehicle %s exited. Fee = PHP %.2f%n",
                            plateNumber, fee);
                    return;
                }
            }
        }

        System.out.println("Exit rejected: Vehicle not found.");
    }

    public double calculateFee(Vehicle vehicle, int hours) {
        if (vehicle.getType().equalsIgnoreCase("MOTORCYCLE")) {
            return 20 + (hours - 1) * 10;
        } else {
            return 40 + (hours - 1) * 20;
        }
    }

    public void displayOccupiedSlots() {
        boolean hasOccupiedSlot = false;

        for (int i = 0; i < slots.length; i++) {
            if (slots[i].isOccupied()) {
                Vehicle vehicle = slots[i].getParkedVehicle();

                System.out.println("Slot " + slots[i].getSlotNumber()
                        + " | Type: " + slots[i].getSlotType()
                        + " | Plate: " + vehicle.getPlateNumber()
                        + " | Owner: " + vehicle.getOwnerName());

                hasOccupiedSlot = true;
            }
        }

        if (!hasOccupiedSlot) {
            System.out.println("No occupied slots.");
        }
    }
}