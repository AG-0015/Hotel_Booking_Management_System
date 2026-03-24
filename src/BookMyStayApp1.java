import java.util.*;
import java.util.concurrent.*;

// Represents a hotel room
class Room {
    private final int roomNumber;
    private boolean isBooked;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isBooked = false;
    }

    // Synchronized to ensure thread-safe booking
    public synchronized boolean book() {
        if (!isBooked) {
            isBooked = true;
            return true;
        }
        return false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }
}

// Represents a booking request by a guest
class BookingRequest {
    private final String guestName;

    public BookingRequest(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestName() {
        return guestName;
    }
}

// Handles booking processing in a thread-safe manner
class BookingProcessor implements Runnable {
    private final Queue<BookingRequest> bookingQueue;
    private final List<Room> rooms;

    public BookingProcessor(Queue<BookingRequest> bookingQueue, List<Room> rooms) {
        this.bookingQueue = bookingQueue;
        this.rooms = rooms;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;
            synchronized (bookingQueue) {
                request = bookingQueue.poll();
            }

            if (request == null) {
                break; // No more requests to process
            }

            boolean booked = false;
            for (Room room : rooms) {
                if (room.book()) {
                    System.out.println("Guest " + request.getGuestName() +
                            " successfully booked Room " + room.getRoomNumber() +
                            " by " + Thread.currentThread().getName());
                    booked = true;
                    break;
                }
            }

            if (!booked) {
                System.out.println("Guest " + request.getGuestName() +
                        " could not book a room. No rooms available.");
            }
        }
    }
}

public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        // Shared booking queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        // Sample rooms
        List<Room> rooms = new ArrayList<>();
        for (int i = 1; i <= 5; i++) { // 5 rooms
            rooms.add(new Room(i));
        }

        // Simulate multiple guests submitting requests
        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"};
        for (String guest : guests) {
            bookingQueue.add(new BookingRequest(guest));
        }

        // Create a thread pool to process bookings concurrently
        int numberOfThreads = 3;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(new BookingProcessor(bookingQueue, rooms), "BookingThread-" + i);
            threads.add(t);
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\nFinal Room Status:");
        for (Room room : rooms) {
            System.out.println("Room " + room.getRoomNumber() + " booked: " + room.isBooked());
        }
    }
}