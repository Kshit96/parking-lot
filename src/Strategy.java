import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

public enum Strategy {

        MAX_FREE_SPACE{
            @Override
            public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
                Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.numberOfFreeSlots() >= anotherParkingLot.numberOfFreeSlots() ? parkingLot : anotherParkingLot);
                parkingLotToParkIn.get().park(car);
            }
        },
        MAX_CAPACITY{
            @Override
            public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
                Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.maximumNumberOfSlots >= anotherParkingLot.maximumNumberOfSlots ? parkingLot : anotherParkingLot);
                parkingLotToParkIn.get().park(car);
            }
    },
        FIRST_AVAILABLE{
            @Override
            public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
                if (!availableParkingLots.isEmpty()) {
                    availableParkingLots.get(0).park(car);
                }
            }
        };

        public abstract void park(Car car, ArrayList<ParkingLot> availableParkingLots);

}
