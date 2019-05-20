import java.util.ArrayList;

public class FirstAvailable implements StrategyPattern {
    @Override
    public void park(Car car, ArrayList<ParkingLot> availableParkingLots) {
        if (!availableParkingLots.isEmpty()) {
            availableParkingLots.get(0).park(car);
        }
    }
}
