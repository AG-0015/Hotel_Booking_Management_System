import java.util.*;
import java.util.concurrent.*;

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

// ------------------- Thread-Safe Room Inventory -------------------
class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Thread-safe method to check and decrement inventory
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

// ------------------- Booking Processor -------------------
class BookingProcessor implements Runnable {
    private final Reservation reservation;
    private final RoomInventory inventory;
    private final List<Reservation> confirmedBookings;

    public BookingProcessor(Reservation reservation, RoomInventory inventory, List<Reservation> confirmedBookings) {
        this.reservation = reservation;
        this.inventory = inventory;
        this.confirmedBookings = confirmedBookings;
    }

    @Override
    public void run() {
        boolean allocated = inventory.allocateRoom(reservation.getRoomType());
        if (allocated) {
            synchronized (confirmedBookings) {
                confirmedBookings.add(reservation);
            }
            System.out.println("Booking Confirmed: " + reservation);
        } else {
            System.out.println("Booking Failed (No availability): " + reservation);
        }
    }
}

// ------------------- Main Application -------------------
public class BookMyStayApp1 {

    public static void main(String[] args) throws InterruptedException {
        RoomInventory inventory = new RoomInventory();
        List<Reservation> confirmedBookings = Collections.synchronizedList(new ArrayList<>());

        // Simulated booking requests from multiple guests
        Reservation[] requests = {
                new Reservation("RES101", "Alice", "Single Room", 2),
                new Reservation("RES102", "Bob", "Double Room", 3),
                new Reservation("RES103", "Charlie", "Suite Room", 1),
                new Reservation("RES104", "Diana", "Single Room", 1),
                new Reservation("RES105", "Eve", "Double Room", 2),
                new Reservation("RES106", "Frank", "Suite Room", 2),
                new Reservation("RES107", "Grace", "Single Room", 3)
        };

        // ExecutorService for concurrent processing
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (Reservation req : requests) {
            executor.submit(new BookingProcessor(req, inventory, confirmedBookings));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("\n=== Final Confirmed Bookings ===");
        synchronized (confirmedBookings) {
            for (Reservation res : confirmedBookings) {
                System.out.println(res);
            }
        }

        inventory.displayInventory();
        System.out.println("\nAll concurrent booking requests processed safely.");
    }
}