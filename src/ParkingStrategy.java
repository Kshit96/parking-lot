import java.util.ArrayList;

public interface ParkingStrategy {

    public ParkingLot getParkingLot(ArrayList<ParkingLot> availableParkingLots);
}
