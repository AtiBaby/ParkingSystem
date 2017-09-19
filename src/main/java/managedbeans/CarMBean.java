package managedbeans;

import service.CarsService;
import lombok.Getter;
import lombok.Setter;
import model.Car;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
@ManagedBean(name="carBean")
@ViewScoped
@Getter
@Setter
public class CarMBean extends AbstractMBean {
    
    private Car newCar = new Car();
    private Car selectedCar = new Car();
    
    private List<Car> cars = new ArrayList<Car>();

    @EJB
    private CarsService carsService;
    
    @PostConstruct
    public void init(){
        cars = carsService.getCars();
    }

    public void createCar(){
        if(requiredFieldsNotEmpty(newCar)) {
            Boolean containIt = false;
            for (Car car : cars) {
                if (newCar.getLicensePlateNumber().equals(car.getLicensePlateNumber())) {
                    containIt = true;
                }
            }
            if (containIt) {
                addFacesMessageForComponents("Ez a rendszám már szerepel a rendszerben!", "lpn");
            } else {
            /* Extra: Rendszám formátumának ellenőrzése. Csak angol ABC betűi, whitespace és kötőjel lehetnek benne.
             Ha valid, akkor az autó hozzáadása sikeresen megtörténik.*/
                String carLPN = newCar.getLicensePlateNumber().toUpperCase();
                if (validLPN(carLPN)) {
                    newCar.setLicensePlateNumber(carLPN);
                    carsService.addCar(newCar);
                    setNewCar(new Car());
                    setCars(carsService.getCars());
                    RequestContext.getCurrentInstance().execute("PF('addCarVar').hide()");
                } else {
                    addFacesMessageForComponents("Rendszámban csak az angol ABC betűi, whitespace karakter és kötőjel szerepelhet!", "lpn");
                }
            }
        }
    }

    private boolean requiredFieldsNotEmpty(Car newCar) {
        boolean notEmpty = true;
        if (newCar.getLicensePlateNumber() == null || StringUtils.isEmpty(newCar.getLicensePlateNumber())) {
            addFacesMessageForComponents("Rendszám megadása kötelező!", "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getBrand() == null || StringUtils.isEmpty(newCar.getBrand())) {
            addFacesMessageForComponents("Autó márka megadása kötelező!", "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getType() == null || StringUtils.isEmpty(newCar.getType())) {
            addFacesMessageForComponents("Autó típusának megadása kötelező!", "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getColor() == null || StringUtils.isEmpty(newCar.getColor())) {
            addFacesMessageForComponents("Autó színének megadása kötelező!", "requiredMessage");
            notEmpty = false;
        }
        return notEmpty;
    }

    private boolean validLPN(String LPN){
        return LPN.matches("[A-Z0-9\\s-]*");
    }

    public void deleteCar(){
        if (selectedCar == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Törléshez válassz ki egy autót!",null));
        } else if (selectedCar.getIsParking()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Az " + selectedCar.getLicensePlateNumber() + " rendszámú autó egy parkolóban van, "
                            + "így nem lehet törölni!",null));
        } else {
            carsService.deleteCar(selectedCar);
            setCars(carsService.getCars());
            setSelectedCar(new Car());
        }
    }
}
