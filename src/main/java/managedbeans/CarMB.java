package managedbeans;

import ejb.CarsService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Car;

/**
 *
 * @author Attila
 */
@ManagedBean(name="carBean")
@ViewScoped
public class CarMB {
    
    private Car newCar = new Car();
    private Car selectedCar = new Car();
    
    private List<Car> cars = new ArrayList<Car>();
    
    @EJB
    private CarsService carsService;
    
    @PostConstruct
    public void init(){
        cars = carsService.getCars();
    }

    public Car getNewCar() {
        return newCar;
    }

    public void setNewCar(Car newCar) {
        this.newCar = newCar;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
