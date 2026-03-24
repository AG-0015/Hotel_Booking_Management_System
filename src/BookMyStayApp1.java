import java.util.*;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 9: Error Handling & Validation
 *
 * Version: 9.0
 */

// ------------------- Custom Exception -------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// ------------------- Reservation Class -------------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) throws InvalidBookingException {
        if (guestName == null || guestName.isEmpty())
            throw new InvalidBookingException("Guest name cannot be empty.");
        if (nights <= 0)
            throw new InvalidBookingException("Number of nights must be greater than zero.");
        if (!Arrays.asList("Single Room", "Double Room", "Suite Room").contains(roomType))
            throw new InvalidBookingException("Invalid room type: " + roomType);

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNights() { return nights; }

    @Override
    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType + " | " + nights + " nights";
    }
}

// ------------------- Room Inventory with Validation -------------------
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType))
            throw new InvalidBookingException("Unknown room type: " + roomType);
        return inventory.get(roomType);
    }

    public void decrementAvailability(String roomType) throws InvalidBookingException {
        int available = getAvailability(roomType);
        if (available <= 0)
            throw new InvalidBookingException("No " + roomType + " available.");
        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

// ------------------- Booking Service with Validation -------------------
class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void allocateRoom(Reservation res) {
        try {
            inventory.decrementAvailability(res.getRoomType());
            System.out.println("Reservation Confirmed: " + res.getGuestName() + " | Room Type: " + res.getRoomType());
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// ------------------- Main Application -------------------
public class BookMyStayApp1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        System.out.println("=== Book My Stay: Error Handling & Validation ===");

        while (true) {
            System.out.print("\nEnter Reservation ID (or 'exit' to quit): ");
            String resId = sc.nextLine();
            if (resId.equalsIgnoreCase("exit")) break;

            System.out.print("Enter Guest Name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter Room Type (Single Room / Double Room / Suite Room): ");
            String roomType = sc.nextLine();

            System.out.print("Enter Number of Nights: ");
            int nights = -1;
            try {
                nights = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for number of nights. Must be a positive integer.");
                continue;
            }

            try {
                Reservation res = new Reservation(resId, guestName, roomType, nights);
                bookingService.allocateRoom(res);
            } catch (InvalidBookingException e) {
                System.out.println("Booking Failed: " + e.getMessage());
            }

            inventory.displayInventory();
        }

        System.out.println("\nExiting System. Goodbye!");
        sc.close();
    }
}