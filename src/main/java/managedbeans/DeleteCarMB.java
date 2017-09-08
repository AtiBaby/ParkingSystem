package managedbeans;

import ejb.CarsService;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.Car;

/**
 *
 * @author Attila
 */
@ManagedBean(name="deleteCarBean")
@RequestScoped
public class DeleteCarMB {
    
    @ManagedProperty("#{carBean}")
    private CarMB carMB;
    
    @EJB
    private CarsService carsService;
    
    public void deleteCar(){
        if (carMB.getSelectedCar() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Törléshez válassz ki egy autót!",null));
        } else if (carMB.getSelectedCar().getIsParking()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Az " + carMB.getSelectedCar().getLicensePlateNumber() + " rendszámú autó egy parkolóban van, "
                    + "így nem lehet törölni!",null));
        } else {
            carsService.deleteCar(carMB.getSelectedCar());
            carMB.setCars(carsService.getCars());
            carMB.setSelectedCar(new Car());
        }
    }

    public CarMB getCarMB() {
        return carMB;
    }

    public void setCarMB(CarMB carMB) {
        this.carMB = carMB;
    }
    
}
