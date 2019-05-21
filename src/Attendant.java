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

        availableParkingLots = parkingLots.stream().filter(parkingLot -> !parkingLot.isFull()).collect(Collectors.toCollection(ArrayList::new));
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
