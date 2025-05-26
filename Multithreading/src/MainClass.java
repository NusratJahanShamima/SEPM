import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        ParkingPool pool = new ParkingPool();

        // Start multiple parking agents
        new ParkingAgent("Agent-1", pool).start();
        new ParkingAgent("Agent-2", pool).start();
        new ParkingAgent("Agent-3", pool).start();

        Scanner scanner = new Scanner(System.in);
        int requestCount = 0;

        while (requestCount < 10) {
            System.out.println("Press Enter to register a new car for parking...or type -1 to exit:");
            String input = scanner.nextLine();
            if (input.equals("-1")) break;

            RegistrarParking request = new RegistrarParking();
            pool.addParkingRequest(request);
            requestCount++;
        }

        scanner.close();
        System.out.println("No more parking requests.");
    }
}
