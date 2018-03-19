package managedbeans;

import model.Address;
import model.CarPark;
import model.utils.Nation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Attila
 */
@ManagedBean(name = "carBean")
@ViewScoped
@Getter
@Setter
public class CarMBean extends AbstractMBean {

    private Car newCar;
    private Car selectedCar = new Car();

    private List<Car> cars = new ArrayList<>();
    private Nation[] allNation = Nation.values();
    private ApplicationContext context = new ClassPathXmlApplicationContext("resourcebundles.xml");

    @EJB
    private CarsService carsService;

    @Inject
    private LanguageMBean languageMBean;

    @PostConstruct
    public void init() {
        cars = carsService.getCars();
        pageViewType = PageViewType.VIEW;
    }

    @Override
    public void showNewEditPanel() {
        super.showNewEditPanel();
        newCar = new Car();
        newCar.setNation(Nation.HUNGARY);
    }

    public void cancelEdit() {
        pageViewType = PageViewType.VIEW;
        newCar = null;
    }

    public void createCar() {
        if (requiredFieldsNotEmpty(newCar)) {
            Boolean containIt = false;
            for (Car car : cars) {
                if (newCar.getLicensePlateNumber().equals(car.getLicensePlateNumber())) {
                    containIt = true;
                }
            }
            if (containIt) {
                addFacesMessageForComponents(context.getMessage("lpn.in.system", null, languageMBean.getLocale()), "lpn");
            } else {
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

        pageViewType = PageViewType.VIEW;
    }

    private boolean requiredFieldsNotEmpty(Car newCar) {
        boolean notEmpty = true;
        if (newCar.getLicensePlateNumber() == null || StringUtils.isEmpty(newCar.getLicensePlateNumber())) {
            addFacesMessageForComponents(context.getMessage("lpn.in.system", null, languageMBean.getLocale()), "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getBrand() == null || StringUtils.isEmpty(newCar.getBrand())) {
            addFacesMessageForComponents(context.getMessage("brand.in.system", null, languageMBean.getLocale()), "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getType() == null || StringUtils.isEmpty(newCar.getType())) {
            addFacesMessageForComponents(context.getMessage("car.type.in.system", null, languageMBean.getLocale()), "requiredMessage");
            notEmpty = false;
        }
        if (newCar.getColor() == null || StringUtils.isEmpty(newCar.getColor())) {
            addFacesMessageForComponents(context.getMessage("color.in.system", null, languageMBean.getLocale()), "requiredMessage");
            notEmpty = false;
        }
        return notEmpty;
    }

    private boolean validLPN(String LPN) {
        return LPN.matches("[A-Z]{3}-[0-9]{3}");
    }

    public void deleteCar() {
        if (selectedCar == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    context.getMessage("select.car.to.delete", null, languageMBean.getLocale()), null));
        } else if (selectedCar.getIsParking()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    context.getMessage("delete.car.inpossible.prefix", null, languageMBean.getLocale())
                            + selectedCar.getLicensePlateNumber() +
                            context.getMessage("delete.car.inpossible.postfix", null, languageMBean.getLocale()), null));
        } else {
            carsService.deleteCar(selectedCar);
            setCars(carsService.getCars());
            setSelectedCar(new Car());
        }
    }
}
