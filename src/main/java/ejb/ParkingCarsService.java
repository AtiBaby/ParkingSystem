package ejb;

import java.util.List;
import model.Car;

/**
 *
 * @author Attila
 */
public interface ParkingCarsService {
    
    void addParkingCar(Car car);
    
    List<Car> getParkingCars();
    
}
