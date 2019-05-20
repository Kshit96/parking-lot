import java.util.ArrayList;
import java.util.Optional;

public class MaxCapacity implements StrategyPattern {
    @Override
    public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.maximumNumberOfSlots >= anotherParkingLot.maximumNumberOfSlots ? parkingLot : anotherParkingLot);
        parkingLotToParkIn.get().park(car);
    }
}
