import java.util.*;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @author AG-0015
 * @version 6.0
 */

// Reservation request class
class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }
}

// Inventory service
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean decrementAvailability(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " Available: " + inventory.get(roomType));
        }
    }
}

// Booking allocation service
class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRooms = new HashMap<>();
        // initialize allocatedRooms map
        for (String roomType : Arrays.asList("Single Room", "Double Room", "Suite Room")) {
            allocatedRooms.put(roomType, new HashSet<>());
        }
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        Set<String> existingIDs = allocatedRooms.get(roomType);
        int id = existingIDs.size() + 1;
        return roomType.replace(" ", "").substring(0, 2).toUpperCase() + "-" + id;
    }

    // Process booking request
    public void allocateRoom(Reservation request) {
        String roomType = request.getRoomType();
        if (inventory.getAvailability(roomType) > 0) {
            String roomID = generateRoomID(roomType);
            allocatedRooms.get(roomType).add(roomID);
            inventory.decrementAvailability(roomType);

            System.out.println("Reservation Confirmed!");
            System.out.println("Guest: " + request.getGuestName());
            System.out.println("Room Type: " + roomType);
            System.out.println("Assigned Room ID: " + roomID);
            System.out.println("---------------------------");
        } else {
            System.out.println("Sorry " + request.getGuestName() + ", no " + roomType + " available.");
            System.out.println("---------------------------");
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("Allocated Rooms:");
        for (String roomType : allocatedRooms.keySet()) {
            System.out.println(roomType + ": " + allocatedRooms.get(roomType));
        }
    }
}

// Main application
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version: 6.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Simulate booking queue (FIFO)
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room", 2));
        bookingQueue.add(new Reservation("Bob", "Double Room", 3));
        bookingQueue.add(new Reservation("Charlie", "Suite Room", 1));
        bookingQueue.add(new Reservation("Diana", "Suite Room", 2)); // Should handle availability

        // Process booking requests in order
        while (!bookingQueue.isEmpty()) {
            Reservation request = bookingQueue.poll();
            bookingService.allocateRoom(request);
        }

        System.out.println("\nFinal Inventory:");
        inventory.displayInventory();

        System.out.println("\nAllocated Rooms Summary:");
        bookingService.displayAllocatedRooms();
    }
}