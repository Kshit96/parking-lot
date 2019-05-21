import java.util.ArrayList;
import java.util.Optional;

public class MaxCapacity implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.maximumNumberOfSlots >= anotherParkingLot.maximumNumberOfSlots ? parkingLot : anotherParkingLot);
        return parkingLotToParkIn.get();
    }
}
