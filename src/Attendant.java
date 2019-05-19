public class Attendant {
    private final ParkingLot parkingLot;

    public Attendant(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public void park(Car car) {
        parkingLot.park(car);
    }
}
