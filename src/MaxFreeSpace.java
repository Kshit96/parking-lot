import java.util.ArrayList;
import java.util.Optional;

public class MaxFreeSpace implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.numberOfFreeSlots() >= anotherParkingLot.numberOfFreeSlots() ? parkingLot : anotherParkingLot);
        return parkingLotToParkIn.get();
    }
}
