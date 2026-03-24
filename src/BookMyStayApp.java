import java.util.*;

// Class representing an Add-On Service
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Manager class for handling Add-On Services
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Get services for a reservation
    public List<Service> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost of services
    public double calculateTotalServiceCost(String reservationId) {
        double total = 0;
        List<Service> services = getServices(reservationId);

        for (Service s : services) {
            total += s.getCost();
        }

        return total;
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservation ID
        String reservationId = "RES123";

        System.out.println("=== Add-On Service Selection ===");
        System.out.println("Reservation ID: " + reservationId);

        while (true) {
            System.out.println("\nChoose Service:");
            System.out.println("1. Breakfast (₹200)");
            System.out.println("2. Airport Pickup (₹500)");
            System.out.println("3. Extra Bed (₹300)");
            System.out.println("4. Done");

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
                    System.out.println("\nSelected Services:");
                    List<Service> services = manager.getServices(reservationId);

                    for (Service s : services) {
                        System.out.println("- " + s);
                    }

                    double total = manager.calculateTotalServiceCost(reservationId);
                    System.out.println("Total Add-On Cost: ₹" + total);

                    System.out.println("\nBooking and room allocation remain unchanged.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}