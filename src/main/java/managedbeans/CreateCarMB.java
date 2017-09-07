package managedbeans;

import com.sun.faces.component.visit.FullVisitContext;
import ejb.CarsService;
import model.Car;
import org.primefaces.context.RequestContext;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import java.util.HashSet;
import java.util.Set;

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
        if(requiredFieldsNotEmpty(carMB.getNewCar())) {
            Boolean containIt = false;
            for (Car car : carMB.getCars()) {
                if (carMB.getNewCar().getLicense_plate_number().equals(car.getLicense_plate_number())) {
                    containIt = true;
                }
            }
            if (containIt) {
                addFacesMessageForComponents("Ez a rendszám már szerepel a rendszerben!", "lpn");
            } else {
            /* Extra: Rendszám formátumának ellenőrzése. Csak angol ABC betűi, whitespace és kötőjel lehetnek benne.
             Ha valid, akkor az autó hozzáadása sikeresen megtörténik.*/
                String carLPN = carMB.getNewCar().getLicense_plate_number().toUpperCase();
                if (validLPN(carLPN)) {
                    carMB.getNewCar().setLicense_plate_number(carLPN);
                    carsService.addCar(carMB.getNewCar());
                    carMB.setNewCar(new Car());
                    carMB.setCars(carsService.getCars());
                    RequestContext.getCurrentInstance().execute("PF('addCar').hide()");
                } else {
                    addFacesMessageForComponents("Rendszámban csak az angol ABC betűi, whitespace karakter és kötőjel szerepelhet!", "lpn");
                }
            }
        }
    }

    private boolean requiredFieldsNotEmpty(Car newCar) {
        boolean notEmpty = true;
        if ("".equals(newCar.getLicensePlateNumber())) {
            addFacesMessageForComponents("Rendszám megadása kötelező!", "lpn");
            notEmpty = false;
        }
        if ("".equals(newCar.getBrand())) {
            addFacesMessageForComponents("Autó márka megadása kötelező!", "brand");
            notEmpty = false;
        }
        if ("".equals(newCar.getType())) {
            addFacesMessageForComponents("Autó típusának megadása kötelező!", "type");
            notEmpty = false;
        }
        if ("".equals(newCar.getColor())) {
            addFacesMessageForComponents("Autó színének megadása kötelező!", "color");
            notEmpty = false;
        }
        return notEmpty;
    }
    
    private boolean validLPN(String LPN){
        return LPN.matches("[A-Z0-9\\s-]*");
    }
 
    public CarMB getCarMB() {
        return carMB;
    }

    public void setCarMB(CarMB carMB) {
        this.carMB = carMB;
    }

    protected void addFacesMessageForComponents(String message, String... componentNames) {
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Set<UIComponent> talaltKomponensek = komponensekKeresese(facesContext, componentNames);
        if (!talaltKomponensek.isEmpty()) {
            for (UIComponent uiComponent : talaltKomponensek) {
                facesContext.addMessage(uiComponent.getClientId(), facesMessage);
            }
        }
    }

    private Set<UIComponent> komponensekKeresese(final FacesContext facesContext, final String... componentNames) {
        final int targetCount = componentNames.length;
        final Set<UIComponent> found = new HashSet<>();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        uiViewRoot.visitTree(new FullVisitContext(facesContext), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                for (String componentName : componentNames) {
                    if (target.getId().equals(componentName)) {
                        found.add(target);
                        if (found.size() == targetCount) {
                            return VisitResult.COMPLETE;
                        }
                    }
                }
                return VisitResult.ACCEPT;
            }
        });
        return found;
    }
    
}
