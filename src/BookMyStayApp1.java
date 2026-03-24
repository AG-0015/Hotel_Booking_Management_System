import java.util.*;

/**
 * Book My Stay - Hotel Booking Management System
 * Use Case 7: Add-On Service Selection
 *
 * Version: 7.0 (Final)
 */

// Represents an Add-On Service
class Service {
    private final String serviceName;
    private final double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manages Add-On Services associated with reservations
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add a service to a reservation
    public void addService(String reservationId, Service service) {
        reservationServices.computeIfAbsent(reservationId, k -> new ArrayList<Service>()).add(service);
    }

    // Retrieve services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<Service>());
    }

    // Calculate total cost of selected services
    public double calculateTotalCost(String reservationId) {
        double total = 0;
        for (Service s : getServices(reservationId)) total += s.getCost();
        return total;
    }

    // Display selected services for a reservation
    public void displayServices(String reservationId) {
        List<Service> services = getServices(reservationId);
        if (services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + reservationId);
            return;
        }

        System.out.println("\nSelected Add-On Services for " + reservationId + ":");
        for (Service s : services) System.out.println("- " + s);
        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

// Main application for Use Case 7
public class BookMyStayApp1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation ID
        String reservationId = "RES101";

        System.out.println("=== Book My Stay: Add-On Service Selection ===");
        System.out.println("Reservation ID: " + reservationId);

        while (true) {
            System.out.println("\nSelect a Service:");
            System.out.println("1. Breakfast (₹200)");
            System.out.println("2. Airport Pickup (₹500)");
            System.out.println("3. Extra Bed (₹300)");
            System.out.println("4. Spa (₹1000)");
            System.out.println("5. Finish Selection");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    manager.addService(reservationId, new Service("Breakfast", 200));
                    break;
                case 2:
                    manager.addService(reservationId, new Service("Airport Pickup", 500));
                    break;
                case 3:
                    manager.addService(reservationId, new Service("Extra Bed", 300));
                    break;
                case 4:
                    manager.addService(reservationId, new Service("Spa", 1000));
                    break;
                case 5:
                    manager.displayServices(reservationId);
                    System.out.println("\nCore booking and inventory remain unchanged.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select 1-5.");
                    break;
            }
        }
    }
}