import java.io.*;
import java.util.*;

// Booking class to hold booking details
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookingId;
    private String customerName;

    public Booking(String bookingId, String customerName) {
        this.bookingId = bookingId;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "BookingID: " + bookingId + ", Customer: " + customerName;
    }
}

// Inventory class to hold inventory state
class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> items = new HashMap<>();

    public void addItem(String itemName, int quantity) {
        items.put(itemName, quantity);
    }

    public void updateItem(String itemName, int quantity) {
        items.put(itemName, quantity);
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }
}

// Persistence Service to handle saving/loading
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public static void saveState(List<Booking> bookings, Inventory inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(bookings);
            oos.writeObject(inventory);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save system state: " + e.getMessage());
        }
    }

    public static Object[] loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No persisted state found. Starting fresh.");
            return new Object[]{new ArrayList<Booking>(), new Inventory()};
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Booking> bookings = (List<Booking>) ois.readObject();
            Inventory inventory = (Inventory) ois.readObject();
            System.out.println("System state loaded successfully.");
            return new Object[]{bookings, inventory};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load system state. Starting fresh. Error: " + e.getMessage());
            return new Object[]{new ArrayList<Booking>(), new Inventory()};
        }
    }
}

public class BookMyStayApp1 {
    public static void main(String[] args) {
        // Load previous state
        Object[] state = PersistenceService.loadState();
        List<Booking> bookings = (List<Booking>) state[0];
        Inventory inventory = (Inventory) state[1];

        // Display current state
        System.out.println("Current Bookings: " + bookings);
        System.out.println("Current Inventory: " + inventory);

        // Example: Add new bookings and update inventory
        Booking newBooking = new Booking("B001", "Alice");
        bookings.add(newBooking);
        inventory.addItem("Laptop", 10);

        System.out.println("After updates:");
        System.out.println("Bookings: " + bookings);
        System.out.println("Inventory: " + inventory);

        // Save state before exit
        PersistenceService.saveState(bookings, inventory);
    }
}