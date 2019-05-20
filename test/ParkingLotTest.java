import org.junit.Test;

import java.util.ArrayList;

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

        verify(owner, times(1)).notifyParkingLotFull();
    }

    @Test
    public void park_shouldNotCallNotifyParkingLotFull_whenParkingLotIsNotFull() {

        Notifiable owner = mock(Notifiable.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        Car car1 = new Car();
        Car car2 = new Car();


        parkingLot.park(car1);

        verify(owner, times(0)).notifyParkingLotFull();
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

        verify(owner, times(1)).notifyParkingLotAvailable();
        verify(trafficCop, times(1)).notifyParkingLotAvailable();
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

        verify(owner, times(1)).notifyParkingLotFull();
        verify(trafficCop, times(1)).notifyParkingLotFull();

    }

    @Test
    public void park_shouldBeCalledByAttendant_whenCarNeedsToBeParked() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        Car car = new Car();

        attendant.park(car);

    }

    @Test
    public void park_shouldCallNotifyParkingLotFullForAllObservers_whenParkingLotIsFullAndCarsAreParkedByAttendant() {

        Notifiable owner = mock(Notifiable.class);
        Notifiable trafficCop = mock(Notifiable.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        parkingLots.get(0).addObserver(owner);
        parkingLots.get(0).addObserver(trafficCop);
        Car car1 = new Car();
        Car car2 = new Car();

        attendant.park(car1);
        attendant.park(car2);

        verify(owner, times(1)).notifyParkingLotFull();
        verify(trafficCop, times(1)).notifyParkingLotFull();

    }

    @Test
    public void park_shouldCallNotifyParkingLotAvailable_whenParkingLotIsFullAndCarIsUnparkedByAttendant() {

        Notifiable owner = mock(Notifiable.class);
        Notifiable trafficCop = mock(Notifiable.class);
        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        parkingLots.get(0).addObserver(owner);
        parkingLots.get(0).addObserver(trafficCop);
        Car car1 = new Car();
        Car car2 = new Car();

        attendant.park(car1);
        attendant.park(car2);
        attendant.unpark(car1);

        verify(owner, times(1)).notifyParkingLotAvailable();
        verify(trafficCop, times(1)).notifyParkingLotAvailable();

    }

    @Test
    public void park_shouldCallNotifyParkingLotAvailableForAttendant_whenParkingLotIsFullAndCarIsUnparkedByAttendant() {

        Notifiable owner = mock(Notifiable.class);
        Notifiable trafficCop = mock(Notifiable.class);
        Notifiable attendant = mock(Attendant.class);
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.addObserver(owner);
        parkingLot.addObserver(trafficCop);
        parkingLot.addObserver(attendant);
        Car car1 = new Car();
        Car car2 = new Car();

        parkingLot.park(car1);
        parkingLot.park(car2);
        parkingLot.unpark(car1);

        verify(owner, times(1)).notifyParkingLotAvailable();
        verify(trafficCop, times(1)).notifyParkingLotAvailable();
        verify(attendant, times(1)).notifyParkingLotAvailable();

    }

    @Test
    public void attendant_shouldHaveMultipleParkingLotsAndBeAbleToPark_whenACarNeedsToBeParked() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        Car car1 = new Car();
        Car car2 = new Car();

        attendant.park(car1);
        attendant.park(car2);


    }

    @Test
    public void park_shouldParkInTheParkingLot2_whenAttendantParksACar() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        Car car = new Car();

        attendant.park(car);

        assertTrue(parkingLots.get(1).hasCar(car));

    }

    @Test
    public void park_shouldPark4thCarInTheParkingLot1_whenAttendantParks4Cars() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(5));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_FREE_SPACE);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();

        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        attendant.park(car4);

        assertTrue(parkingLots.get(0).hasCar(car1));
        assertTrue(parkingLots.get(0).hasCar(car2));
        assertTrue(parkingLots.get(0).hasCar(car3));
        assertTrue(parkingLots.get(1).hasCar(car4));

    }
    @Test
    public void park_shouldParkAllCarsInTheParkingLot1_whenAttendantParks5Cars() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(5));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.MAX_CAPACITY);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        Car car5 = new Car();

        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        attendant.park(car4);
        attendant.park(car5);

        assertTrue(parkingLots.get(0).hasCar(car1));
        assertTrue(parkingLots.get(0).hasCar(car2));
        assertTrue(parkingLots.get(0).hasCar(car3));
        assertTrue(parkingLots.get(0).hasCar(car4));
        assertTrue(parkingLots.get(0).hasCar(car5));

    }
    @Test
    public void park_shouldParkThreeCarsInTheParkingLot1AndTwoCarsInParkingLot2_whenAttendantParks5Cars() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.FIRST_AVAILABLE);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        Car car5 = new Car();

        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        attendant.park(car4);
        attendant.park(car5);

        assertTrue(parkingLots.get(0).hasCar(car1));
        assertTrue(parkingLots.get(0).hasCar(car2));
        assertTrue(parkingLots.get(1).hasCar(car3));
        assertTrue(parkingLots.get(1).hasCar(car4));
        assertTrue(parkingLots.get(1).hasCar(car5));

    }

    @Test
    public void unpark_shouldUnparkAParkedCar_whenAttendantUnparksCar() {

        ArrayList<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(2));
        parkingLots.add(new ParkingLot(3));
        Attendant attendant = new Attendant(parkingLots, Strategy.FIRST_AVAILABLE);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();
        Car car5 = new Car();

        attendant.park(car1);
        attendant.park(car2);
        attendant.park(car3);
        attendant.park(car4);
        attendant.park(car5);

        attendant.unpark(car5);
        attendant.unpark(car4);
        attendant.unpark(car3);
        attendant.park(car5);
        attendant.park(car4);
        attendant.park(car3);

    }
}
