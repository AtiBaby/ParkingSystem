package managedbeans;

import lombok.Getter;
import lombok.Setter;
import model.Car;
import service.CarsService;
import service.ParkingCarsService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Attila
 */
@ManagedBean(name="parkingBean")
@ViewScoped
@Getter
@Setter
public class ParkingMBean extends AbstractMBean {

    private Car selectedCar = new Car();
    private List<Car> parkingCars = new ArrayList();

    private Date startDate;
    private Date endDate;
    
    @ManagedProperty("#{carBean}")
    private CarMBean carMB;
    
    @EJB
    private CarsService carsService;
    
    @EJB
    private ParkingCarsService parkingCarsService;
    
    public void goParking(){
        if (carMB.getSelectedCar() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Parkoláshoz válassz ki egy autót!",null));
        } else {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ec.redirect(ec.getRequestContextPath() + "/parking.xhtml?LPN=" + carMB.getSelectedCar().getLicensePlateNumber());
            } catch (IOException e) {
                System.out.println("Bad redirect.");
            }
        }
    }
    
    public void onLoad(){
        String LPN = selectedCar.getLicensePlateNumber();
        setSelectedCar(carsService.getCarByLPN(LPN));
        setParkingCars(parkingCarsService.getParkingCars());
    }
    
    public void executeParking(String str){
        /*Az inputból date típust kapok vissza, amit át kell alakítani LocalDateTime típussá. Valamint ellenőrzőm azt, hogy
          a végidőpont nincs időben hamarabb, mint a kezdő időpont.*/
        LocalDateTime startDatetime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDatetime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (getDuration(startDatetime, endDatetime) > 0){
            selectedCar.setIsParking(Boolean.TRUE);
            selectedCar.setParkingPlace(str);
            selectedCar.setStartTime(startDatetime);
            selectedCar.setEndTime(endDatetime);
            parkingCarsService.addParkingCar(selectedCar);
            setParkingCars(parkingCarsService.getParkingCars());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "A parkolás vége nem lehet korábban, mint a parkolás kezdete!",null));
        }
    }
    
    public long getDuration(LocalDateTime start, LocalDateTime end) {
        Instant startInstant = start.toInstant(ZoneOffset.UTC);         
        Instant endInstant   =   end.toInstant(ZoneOffset.UTC);
        long duration=Duration.between(startInstant, endInstant).toNanos();
    return duration;
    }
}
