public class ParkingAgent extends Thread {
    private final ParkingPool pool;
    private final String agentName;

    public ParkingAgent(String name, ParkingPool pool) {
        this.agentName = name;
        this.pool = pool;
    }

    public void run() {
        while (true) {
            RegistrarParking request = pool.getParkingRequest();
            if (request != null) {
                System.out.println(agentName + " parked car with ID: " + request.getParkingId());
                try {
                    Thread.sleep(1000); // Simulate parking time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
