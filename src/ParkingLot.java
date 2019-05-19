import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

/*
Simulate a parking lot
 */
public class ParkingLot extends Observable {

    HashSet<Car> cars = new HashSet<>();
    int maximumNumberOfSlots;
    ArrayList<Notifiable> observers = new ArrayList<>();

    ParkingLot(int totalSlots) {
        maximumNumberOfSlots = totalSlots;
    }

    private boolean isFull(){
        if(cars.size()==maximumNumberOfSlots){
            return true;
        }
        return false;
    }

    public void park(Car car) throws CarAlreadyParkedException, ParkingLotFullException {

        if (hasCar(car)) {
            throw new CarAlreadyParkedException();
        }

        if (isFull()) {
            throw new ParkingLotFullException();
        }

        cars.add(car);

        if(isFull()){
            notifyAllObservers(true);
        }
    }

    private void notifyAllObservers(boolean isFull) {
        if(isFull) {
            for (Notifiable observer : observers) {
                observer.notifyParkingLotFull();
            }
            return;
        }
        for (Notifiable observer : observers) {
            observer.notifyParkingLotAvailable();
        }

    }

    public boolean hasCar(Car car) {

        return cars.contains(car);
    }

    public void unpark(Car car) throws CarNotParkedException {

        if (!hasCar(car)) {
            throw new CarNotParkedException();
        }

        if(isFull()){
            notifyAllObservers(false);
        }

        cars.remove(car);
    }

    public void addObserver(Notifiable observer) {
        observers.add(observer);
    }
}
