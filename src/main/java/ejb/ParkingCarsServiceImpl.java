package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

import javax.ejb.Stateless;
import model.Car;

/**
 *
 * @author Attila
 */
@Stateless(name="ParkingCarsService", mappedName="ParkingCarsService")
@Local(ParkingCarsService.class)
public class ParkingCarsServiceImpl implements ParkingCarsService{

    @Override
    public void addParkingCar(Car car) {
        Car.cars.remove(car);
        for (Car car1: Car.cars){
            System.out.println(car1.getLicense_plate_number());
        }
        Car.cars.add(car);
        for (Car car1: Car.cars){
            System.out.println(car1.getLicense_plate_number());
        }
    }

    @Override
    public List<Car> getParkingCars() {
        List<Car> parkingCars = new ArrayList();
        for (Car car: Car.cars){
            if (car.getIsParking()){
                parkingCars.add(car);
            }
        }
        return parkingCars;
    }
    
}
