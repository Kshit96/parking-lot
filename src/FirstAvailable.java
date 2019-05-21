import java.util.ArrayList;

public class FirstAvailable implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots) {
        if (!availableParkingLots.isEmpty()) {
            return availableParkingLots.get(0);
        }
        throw new ParkingLotFullException();
    }
}
