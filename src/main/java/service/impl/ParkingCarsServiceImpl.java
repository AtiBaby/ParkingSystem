package service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

import javax.ejb.Stateless;
import model.Car;
import service.ParkingCarsService;

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
            System.out.println(car1.getLicensePlateNumber());
        }
        Car.cars.add(car);
        for (Car car1: Car.cars){
            System.out.println(car1.getLicensePlateNumber());
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
