import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * @author AG-0015
 * @version 5.0
 */

// Reservation class representing a guest booking request
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + ", Room: " + roomType + ", Nights: " + nights);
    }
}

// Main application class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version: 5.0\n");

        // Queue to hold booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulate incoming booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room", 2));
        bookingQueue.add(new Reservation("Bob", "Double Room", 3));
        bookingQueue.add(new Reservation("Charlie", "Suite Room", 1));

        System.out.println("Booking requests received (in order):\n");

        // Process the queue in FIFO order (just display for now)
        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll(); // removes head of the queue
            reservation.displayReservation();
        }
    }
}