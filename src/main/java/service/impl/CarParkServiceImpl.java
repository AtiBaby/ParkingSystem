package service.impl;

import model.CarPark;
import service.CarParkService;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by holhosa on 2017.09.20..
 */
@Stateless(name="CarParkService", mappedName="CarParkService")
public class CarParkServiceImpl implements CarParkService {

    @Override
    public void addCarPark(CarPark carPark) {
        CarPark.carParks.add(carPark);
    }

    @Override
    public List<CarPark> getAllCarPark() {
        return CarPark.carParks;
    }
}
