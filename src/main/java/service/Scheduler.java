package service;

import managedbeans.CarMBean;
import model.Car;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;
import javax.inject.Inject;
import java.time.ZonedDateTime;

/**
 * Created by holhosa on 2017.09.05..
 */
@Singleton
@Startup
public class Scheduler {

    @Resource
    TimerService timerService;

    @Inject
    private CarMBean carMB;

    /*Every 5 second it checks that parking time of one car is expired.*/
    @Schedule(second = "*/5", minute = "*", hour = "*", persistent = false)
    public void parkingTimeExpired() {
        for (Car car : carMB.getCars()) {
            if (car.getIsParking() && car.getEndTime().isBefore(ZonedDateTime.now().toLocalDateTime())) {
                car.setIsParking(false);
            }
        }
    }
}
