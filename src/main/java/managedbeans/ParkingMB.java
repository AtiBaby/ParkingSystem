package managedbeans;

import ejb.CarsService;
import ejb.ParkingCarsService;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Car;

/**
 *
 * @author Attila
 */
@ManagedBean(name="parkingBean")
@RequestScoped
public class ParkingMB {
    
    @ManagedProperty("#{selectionBean}")
    private SelectionMB selectionMB;
    
    @ManagedProperty("#{carBean}")
    private CarMB carMB;
    
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
                ec.redirect(ec.getRequestContextPath() + "/parking.xhtml?LPN=" + carMB.getSelectedCar().getLicense_plate_number());
            } catch (IOException e) {
                System.out.println("Bad redirect.");
            }
        }
    }
    
    public void onLoad(){
        String LPN = selectionMB.getSelectedCar().getLicense_plate_number();
        selectionMB.setSelectedCar(carsService.getCarByLPN(LPN));
        selectionMB.setParkingCars(parkingCarsService.getParkingCars());
    }
    
    public void executeParking(String str){
        /*Az inputból date típust kapok vissza, amit át kell alakítani LocalDateTime típussá. Valamint ellenőrzőm azt, hogy
          a végidőpont nincs időben hamarabb, mint a kezdő időpont.*/
        LocalDateTime startDatetime = (selectionMB.getStartDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDatetime = (selectionMB.getEndDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (getDuration(startDatetime, endDatetime) > 0){
            Car newparkingCar = selectionMB.getSelectedCar();
            newparkingCar.setIsParking(Boolean.TRUE);
            newparkingCar.setParkingPlace(str);
            newparkingCar.setStartTime(startDatetime);
            newparkingCar.setEndTime(endDatetime);
            parkingCarsService.addParkingCar(newparkingCar);
            selectionMB.setSelectedCar(newparkingCar);
            selectionMB.setParkingCars(parkingCarsService.getParkingCars());
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

    public SelectionMB getSelectionMB() {
        return selectionMB;
    }

    public void setSelectionMB(SelectionMB selectionMB) {
        this.selectionMB = selectionMB;
    }

    public CarMB getCarMB() {
        return carMB;
    }

    public void setCarMB(CarMB carMB) {
        this.carMB = carMB;
    }
}
