import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Attendant implements Notifiable {
    private ArrayList<ParkingLot> parkingLots;
    private ArrayList<ParkingLot> availableParkingLots;
    Strategy strategy;

    public Attendant(ArrayList<ParkingLot> parkingLots, Strategy strategy) {
        this.parkingLots = parkingLots;
        this.strategy = strategy;
        this.availableParkingLots = parkingLots.stream().filter(parkingLot -> !parkingLot.isFull()).collect(Collectors.toCollection(ArrayList::new));
    }

    public void park(Car car) {
        if (strategy == Strategy.MAX_FREE_SPACE) {
            parkByMaximumFreeSpace(car);
        } else if (strategy == Strategy.MAX_CAPACITY) {
            parkByMaximumCapacity(car);
        } else if (strategy == Strategy.FIRST_AVAILABLE) {

            parkByFirstAvailable(car);
        }
        availableParkingLots = parkingLots.stream().filter(parkingLot -> !parkingLot.isFull()).collect(Collectors.toCollection(ArrayList::new));
    }

    private void parkByFirstAvailable(Car car) {
        if (!availableParkingLots.isEmpty()) {
            availableParkingLots.get(0).park(car);
        }
    }

    private void parkByMaximumCapacity(Car car) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.maximumNumberOfSlots >= anotherParkingLot.maximumNumberOfSlots ? parkingLot : anotherParkingLot);
        parkingLotToParkIn.get().park(car);
    }

    private void parkByMaximumFreeSpace(Car car) {
        Optional<ParkingLot> parkingLotToParkIn = availableParkingLots.stream().reduce((parkingLot, anotherParkingLot) -> parkingLot.numberOfFreeSlots() >= anotherParkingLot.numberOfFreeSlots() ? parkingLot : anotherParkingLot);
        parkingLotToParkIn.get().park(car);
    }


    public void unpark(Car car) {
        for(ParkingLot parkingLot: parkingLots){
            if(parkingLot.cars.contains(car)){
                parkingLot.unpark(car);
            }
        }
        availableParkingLots = parkingLots.stream().filter(parkingLot -> !parkingLot.isFull()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void notifyParkingLotFull() {

    }

    @Override
    public void notifyParkingLotAvailable() {

    }
}
