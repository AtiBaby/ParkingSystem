package managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Car;

/**
 *
 * @author Attila
 */
@ManagedBean(name="selectionBean")
@ViewScoped
public class SelectionMB {
    
    private Car selectedCar = new Car();
    private List<Car> parkingCars = new ArrayList();
    
    private Date startDate;
    private Date endDate;

    public List<Car> getParkingCars() {
        return parkingCars;
    }

    public void setParkingCars(List<Car> parkingCars) {
        this.parkingCars = parkingCars;
    }
    
    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
