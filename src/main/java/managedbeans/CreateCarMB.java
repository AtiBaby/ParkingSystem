package managedbeans;

import ejb.CarsService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.Car;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Attila
 */
@ManagedBean(name="createCarBean")
@RequestScoped
public class CreateCarMB {
    
    @ManagedProperty("#{carBean}")
    private CarMB carMB;
    
    @EJB
    private CarsService carsService;

    public void doCreate(){
        Boolean containIt = false;
        for(Car car: carMB.getCars()){
            if(carMB.getNewCar().getLicense_plate_number().equals(car.getLicense_plate_number())){
                containIt = true;
            }
        }
        if(containIt){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ez a rendszám már szerepel a rendszerben!",null));
        } else {
            /* Extra: Rendszám formátumának ellenőrzése. Csak angol ABC betűi, whitespace és kötőjel lehetnek benne.
             Ha valid, akkor az autó hozzáadása sikeresen megtörténik.*/
            String carLPN = carMB.getNewCar().getLicense_plate_number().toUpperCase();
            if (validLPN(carLPN)){
                carMB.getNewCar().setLicense_plate_number(carLPN);
                carsService.addCar(carMB.getNewCar());
                carMB.setNewCar(new Car());
                carMB.setCars(carsService.getCars());
                RequestContext.getCurrentInstance().execute("PF('addCar').hide()");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Rendszámban csak az angol ABC betűi, whitespace karakter és kötőjel szerepelhet!",null));
            }
        }
    }
    
    public boolean validLPN(String LPN){
        return LPN.matches("[A-Z0-9\\s-]*");
    }
 
    public CarMB getCarMB() {
        return carMB;
    }

    public void setCarMB(CarMB carMB) {
        this.carMB = carMB;
    }
    
}
