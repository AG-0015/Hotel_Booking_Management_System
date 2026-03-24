import java.util.*;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 8: Booking History & Reporting
 *
 * Version: 8.0 (Final)
 */

// Booking class
class Booking {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalAmount;

    public Booking(String reservationId, String guestName, String roomType, double totalAmount) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalAmount = totalAmount;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Amount: ₹" + totalAmount;
    }
}

// Booking history manager
class BookingHistoryManager {

    private List<Booking> bookingList = new ArrayList<>();

    // Add a booking to history
    public void addBooking(Booking booking) {
        bookingList.add(booking);
    }

    // Display all bookings
    public void displayAllBookings() {
        if (bookingList.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n=== Booking History ===");
        for (Booking b : bookingList) {
            System.out.println(b);
        }
    }

    // Calculate total revenue
    public double calculateTotalRevenue() {
        double total = 0;
        for (Booking b : bookingList) {
            total += b.getTotalAmount();
        }
        return total;
    }
}

// Main class
public class BookMyStayApp1 {

    public static void main(String[] args) {

        BookingHistoryManager historyManager = new BookingHistoryManager();

        // Simulate confirmed bookings
        historyManager.addBooking(new Booking("RES101", "Alice", "Deluxe", 2500));
        historyManager.addBooking(new Booking("RES102", "Bob", "Suite", 4000));
        historyManager.addBooking(new Booking("RES103", "Charlie", "Standard", 1500));

        // Display booking history
        historyManager.displayAllBookings();

        // Display total revenue
        double totalRevenue = historyManager.calculateTotalRevenue();
        System.out.println("\nTotal Revenue: ₹" + totalRevenue);
    }
}