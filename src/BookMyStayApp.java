import java.util.HashMap;
import java.util.Map;

// Room classes (reusing previous definition)
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size;
    protected double price;

    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: ₹" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 3000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 5000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500, 9000);
    }
}

// Inventory class (read-only for search)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // example: Suite not available
    }

    // Read-only access method
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public HashMap<String, Integer> getInventorySnapshot() {
        // Defensive copy to prevent modifications
        return new HashMap<>(inventory);
    }
}

// Main search application
public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version: 4.0\n");

        // Initialize rooms and inventory
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };
        RoomInventory inventory = new RoomInventory();

        System.out.println("Available Rooms:\n");

        // Search logic: read-only, only display rooms with availability > 0
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("---------------------------");
            }
        }
    }
}