package ejb;

import java.util.List;
import model.Car;

/**
 *
 * @author Attila
 */
public interface CarsService {

    void addCar(Car car);
    
    List<Car> getCars();
    
    Car getCarByLPN(String LPN);
    
    void deleteCar(Car car);
}
