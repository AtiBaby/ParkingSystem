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

        //Shared coordinates
//        LatLng coord1 = new LatLng(36.879466, 30.667648);
//        LatLng coord2 = new LatLng(36.883707, 30.689216);
//        LatLng coord3 = new LatLng(36.879703, 30.706707);
//        LatLng coord4 = new LatLng(36.885233, 30.702323);
//
//        //Basic marker
//        simpleModel.addOverlay(new Marker(coord1, "Konyaalti"));
//        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
//        simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
//        simpleModel.addOverlay(new Marker(coord4, "Kaleici"));
    }

    public void addCarPark(CarPark carPark) {
        if (carPark.getXcoordinate() != null & carPark.getYcoordinate() != null) {
            LatLng coord = new LatLng(carPark.getXcoordinate(), carPark.getYcoordinate());
            simpleModel.addOverlay(new Marker(coord, carPark.getName(),"", "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {

    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
}
