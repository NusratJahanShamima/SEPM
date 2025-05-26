public class RegistrarParking {
    private static int count = 0;
    private final int parkingId;

    public RegistrarParking() {
        this.parkingId = ++count;
    }

    public int getParkingId() {
        return parkingId;
    }
}
