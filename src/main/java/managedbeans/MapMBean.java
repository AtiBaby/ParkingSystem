package managedbeans;

import lombok.Getter;
import model.CarPark;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * Created by holhosa on 2017.09.20..
 */
@ManagedBean(name="mapMBean")
@RequestScoped
public class MapMBean {

    private MapModel simpleModel;
    @Getter
    private Marker selectedMarker;
    @Getter
    private CarPark selectedCarPark;

    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        List<CarPark> carParks = CarPark.carParks;
        LatLng coord1 = new LatLng(47.532089, 21.624479);
        simpleModel.addOverlay(new Marker(coord1, "Nagytemplomi parkoló", "", "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
        for (CarPark carPark : carParks) {
            LatLng coord = new LatLng(carPark.getXcoordinate().doubleValue(), carPark.getYcoordinate().doubleValue());
            simpleModel.addOverlay(new Marker(coord, carPark.getName(), carPark, "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
        }
    }

    public void addCarPark(CarPark carPark) {
        if (carPark.getXcoordinate() != null & carPark.getYcoordinate() != null) {
            LatLng coord = new LatLng(carPark.getXcoordinate().doubleValue(), carPark.getYcoordinate().doubleValue());
            simpleModel.addOverlay(new Marker(coord, carPark.getName(), carPark, "http://localhost:8080/parkingSystem/javax.faces.resource/melygarazs.png.xhtml?ln=images"));
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        selectedMarker = (Marker) event.getOverlay();
        selectedCarPark = (CarPark) selectedMarker.getData();
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }
}
