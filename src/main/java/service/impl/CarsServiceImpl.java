package service.impl;

import java.util.List;
import model.Car;
import service.CarsService;

import javax.ejb.Stateless;

/**
 *
 * @author Attila
 */
@Stateless(name="CarsService", mappedName="CarsService")
public class CarsServiceImpl implements CarsService {

    @Override
    public void addCar(Car car) {
        Car.cars.add(car);
    }

    @Override
    public List<Car> getCars() {
        return Car.cars;
    }
    
    @Override
    public Car getCarByLPN(String LPN) {
        for (Car car: Car.cars){
            System.out.println(car.getLicensePlateNumber());
            if (LPN.equals(car.getLicensePlateNumber())){
                return car;
            }
        }
        return null;
    }
    
    @Override
    public void deleteCar(Car car) {
        Car.cars.remove(car);
    }
}
