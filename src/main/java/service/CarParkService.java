package service;

import model.CarPark;

import java.util.List;

/**
 * Created by holhosa on 2017.09.20..
 */
public interface CarParkService {

    void addCarPark(CarPark carPark);

    List<CarPark> getAllCarPark();
}
