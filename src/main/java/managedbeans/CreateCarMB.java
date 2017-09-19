package managedbeans;

import com.sun.faces.component.visit.FullVisitContext;
import ejb.CarsService;
import model.Car;
import org.apache.commons.lang3.StringUtils;
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
                if (carMB.getNewCar().getLicensePlateNumber().equals(car.getLicensePlateNumber())) {
                    containIt = true;
                }
            }
            if (containIt) {
                addFacesMessageForComponents("Ez a rendszám már szerepel a rendszerben!", "lpn");
            } else {
            /* Extra: Rendszám formátumának ellenőrzése. Csak angol ABC betűi, whitespace és kötőjel lehetnek benne.
             Ha valid, akkor az autó hozzáadása sikeresen megtörténik.*/
                String carLPN = carMB.getNewCar().getLicensePlateNumber().toUpperCase();
                if (validLPN(carLPN)) {
                    carMB.getNewCar().setLicensePlateNumber(carLPN);
                    carsService.addCar(carMB.getNewCar());
                    carMB.setNewCar(new Car());
                    carMB.setCars(carsService.getCars());
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
