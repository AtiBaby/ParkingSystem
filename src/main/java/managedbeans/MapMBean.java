package managedbeans;

import model.CarPark;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by holhosa on 2017.09.20..
 */
@ManagedBean(name="mapMBean")
@ApplicationScoped
public class MapMBean {

    private MapModel simpleModel;

    private Set<CarPark> carsOnMap;

    @PostConstruct
    public void init() {
        carsOnMap = new HashSet<>();
        simpleModel = new DefaultMapModel();

        LatLng coord1 = new LatLng(47.532089,21.624479);
        simpleModel.addOverlay(new Marker(coord1, "Nagytemplomi parkoló", "", "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
    }

    public void addCarPark(CarPark carPark) {
        if (carPark.getXcoordinate() != null & carPark.getYcoordinate() != null) {
            LatLng coord = new LatLng(carPark.getXcoordinate().doubleValue(), carPark.getYcoordinate().doubleValue());
            simpleModel.addOverlay(new Marker(coord, carPark.getName(),"", "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {

    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
}
