import java.util.ArrayList;
import java.util.Optional;

public class MaxFreeSpace implements StrategyPattern {
    @Override
    public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.numberOfFreeSlots() >= anotherParkingLot.numberOfFreeSlots() ? parkingLot : anotherParkingLot);
        parkingLotToParkIn.get().park(car);
    }
}
