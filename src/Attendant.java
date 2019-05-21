import java.util.ArrayList;
import java.util.stream.Collectors;

public class Attendant implements Notifiable {
    private ArrayList<ParkingLot> parkingLots;
    private ArrayList<ParkingLot> availableParkingLots;
    ParkingStrategy strategy;

    public Attendant(ArrayList<ParkingLot> parkingLots, ParkingStrategy strategy) {
        this.parkingLots = parkingLots;
        this.strategy = strategy;
        this.availableParkingLots = parkingLots.stream().filter(parkingLot -> !parkingLot.isFull()).collect(Collectors.toCollection(ArrayList::new));
    }

    public void park(Car car) {

        ParkingLot parkingLotToParkIn = strategy.getParkingLot(availableParkingLots);

        parkingLotToParkIn.park(car);
    }

    public void unpark(Car car) {
        for(ParkingLot parkingLot: parkingLots){
            if(parkingLot.cars.contains(car)){
                parkingLot.unpark(car);
                return;
            }
        }
        throw new CarNotParkedException();
    }

    @Override
    public void notifyParkingLotFull(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);
    }

    @Override
    public void notifyParkingLotAvailable(ParkingLot parkingLot) {
        availableParkingLots.add(parkingLot);
    }
}
