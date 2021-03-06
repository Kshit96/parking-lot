import java.util.ArrayList;
import java.util.HashSet;

/*
Simulate a parking lot
 */
public class ParkingLot {

    HashSet<Car> cars = new HashSet<>();
    int maximumNumberOfSlots;
    ArrayList<Notifiable> observers = new ArrayList<>();

    ParkingLot(int totalSlots) {
        maximumNumberOfSlots = totalSlots;
    }

    public boolean isFull() {
        return cars.size() == maximumNumberOfSlots;
    }

    public int numberOfFreeSlots(){
        return maximumNumberOfSlots - cars.size();
    }

    public void park(Car car) throws CarAlreadyParkedException, ParkingLotFullException {

        if (hasCar(car)) {
            throw new CarAlreadyParkedException();
        }

        if (isFull()) {
            throw new ParkingLotFullException();
        }

        cars.add(car);

        if (isFull()) {
            for (Notifiable observer : observers) {
                observer.notifyParkingLotFull(this);
            }
        }
    }

    public boolean hasCar(Car car) {

        return cars.contains(car);
    }

    public void unpark(Car car) throws CarNotParkedException {

        if (!hasCar(car)) {
            throw new CarNotParkedException();
        }

        cars.remove(car);

        if (cars.size() == maximumNumberOfSlots-1) {
            for (Notifiable observer : observers) {
                observer.notifyParkingLotAvailable(this);
            }
        }
    }

    public void addObserver(Notifiable observer) {
        observers.add(observer);
    }
}
