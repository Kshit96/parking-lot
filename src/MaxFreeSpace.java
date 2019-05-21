import java.util.ArrayList;
import java.util.Optional;

public class MaxFreeSpace implements ParkingStrategy {
    @Override
    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots) {

        if(availableParkingLots.isEmpty()){
            throw new ParkingLotFullException();
        }

        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots
                .stream()
                .reduce((parkingLot, anotherParkingLot) -> parkingLot.numberOfFreeSlots() >= anotherParkingLot.numberOfFreeSlots() ? parkingLot : anotherParkingLot);
        return parkingLotToParkIn.get();
    }
}
