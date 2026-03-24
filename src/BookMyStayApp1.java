import java.util.*;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 10: Booking Cancellation
 *
 * Version: 10.0
 */

// ------------------- Reservation Class -------------------
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
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

// ------------------- Room Inventory -------------------
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void incrementAvailability(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

// ------------------- Booking Manager -------------------
class BookingManager {
    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private RoomInventory inventory;

    public BookingManager(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Add a new booking
    public void addBooking(Reservation res) {
        confirmedBookings.put(res.getReservationId(), res);
        System.out.println("Booking Confirmed: " + res);
    }

    // Cancel an existing booking
    public void cancelBooking(String reservationId) {
        Reservation res = confirmedBookings.remove(reservationId);
        if (res != null) {
            inventory.incrementAvailability(res.getRoomType());
            System.out.println("Booking Cancelled: " + res);
        } else {
            System.out.println("Cancellation Failed: Reservation ID " + reservationId + " not found.");
        }
    }

    // Display all confirmed bookings
    public void displayBookings() {
        if (confirmedBookings.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        System.out.println("\nConfirmed Bookings:");
        for (Reservation res : confirmedBookings.values()) {
            System.out.println("- " + res);
        }
    }
}

// ------------------- Main Application -------------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();
        BookingManager bookingManager = new BookingManager(inventory);

        // Sample bookings
        bookingManager.addBooking(new Reservation("RES101", "Alice", "Single Room", 2));
        bookingManager.addBooking(new Reservation("RES102", "Bob", "Double Room", 3));

        while (true) {
            System.out.println("\n=== Booking Management ===");
            System.out.println("1. View Confirmed Bookings");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 1-4.");
                continue;
            }

            switch (choice) {
                case 1:
                    bookingManager.displayBookings();
                    break;
                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String resId = sc.nextLine();
                    bookingManager.cancelBooking(resId);
                    break;
                case 3:
                    inventory.displayInventory();
                    break;
                case 4:
                    System.out.println("Exiting system. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select 1-4.");
            }
        }
    }
}