import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParkingLotTest {
    @Test
    public void park_shouldParkCarAndReturnTrue_whenTheCarParkActionIsPermformedOnUnparkedCar() {

        Owner owner = mock(Owner.class);
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car();

        parkingLot.park(car);

        assertTrue(parkingLot.hasCar(car));
    }

    @Test
    public void park_shouldReturnTrue_whenTheCarIsAlreadyParkedInASlot() {

        Owner owner = mock(Owner.class);
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car();

        parkingLot.park(car);

        assertTrue(parkingLot.hasCar(car));
    }

    @Test(expected = CarAlreadyParkedException.class)
    public void park_shouldThrowException_whenTheCarParkIsAlreadyParked() {

        Owner owner = mock(Owner.class);
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car();

        parkingLot.park(car);

        parkingLot.park(car);
    }

    @Test(expected = CarNotParkedException.class)
    public void unpark_shouldThrowException_whenUnparkedCarIsUnparked() {

        Owner owner = mock(Owner.class);
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car();

        parkingLot.unpark(car);
    }

    @Test
    public void unpark_shouldReturnFalse_whenParkedCarIsUnparked() {

        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car();

        parkingLot.park(car);
        parkingLot.unpark(car);

        assertFalse(parkingLot.hasCar(car));
    }

    @Test(expected = ParkingLotFullException.class)
    public void park_shouldReturnError_whenCarTriesToParkAndParkingLotIsFull() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        parkingLot.park(car1);
        parkingLot.park(car2);

        parkingLot.park(car3);
    }

    @Test
    public void park_shouldCallNotifyParkingLotFull_whenParkingLotIsFull() {

        Notifiable owner = mock(Notifiable.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        Car car1 = new Car();
        Car car2 = new Car();


        parkingLot.park(car1);
        parkingLot.park(car2);

        verify(owner, times(1)).notifyParkingLotFull(parkingLot);
    }

    @Test
    public void park_shouldNotCallNotifyParkingLotFull_whenParkingLotIsNotFull() {

        Notifiable owner = mock(Notifiable.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        Car car1 = new Car();
        Car car2 = new Car();


        parkingLot.park(car1);

        verify(owner, times(0)).notifyParkingLotFull(parkingLot);
    }

    @Test
    public void park_shouldCallNotifyParkingLotAvailableForAllObservers_whenParkingLotSpaceBecomesAvailable() {

        Notifiable owner = mock(Notifiable.class);
        Notifiable trafficCop = mock(Notifiable.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        parkingLot.addObserver(trafficCop);
        Car car1 = new Car();
        Car car2 = new Car();

        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.unpark(car1);

        verify(owner, times(1)).notifyParkingLotAvailable(parkingLot);
        verify(trafficCop, times(1)).notifyParkingLotAvailable(parkingLot);
    }

    @Test
    public void park_shouldCallNotifyParkingLotFullForAllObservers_whenParkingLotSpaceBecomesAvailable() {

        Notifiable owner = mock(Notifiable.class);
        Notifiable trafficCop = mock(Notifiable.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        parkingLot.addObserver(trafficCop);
        Car car1 = new Car();
        Car car2 = new Car();

        parkingLot.park(car1);
        parkingLot.park(car2);

        verify(owner, times(1)).notifyParkingLotFull(parkingLot);
        verify(trafficCop, times(1)).notifyParkingLotFull(parkingLot);

    }

}
