import java.util.ArrayList;
import java.util.Optional;

public class MaxCapacity implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots) {

        if(availableParkingLots.isEmpty()){
            throw new ParkingLotFullException();
        }

        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots
                .stream()
                .reduce((parkingLot, anotherParkingLot) -> parkingLot.maximumNumberOfSlots >= anotherParkingLot.maximumNumberOfSlots ? parkingLot : anotherParkingLot);
        return parkingLotToParkIn.get();
    }
}
