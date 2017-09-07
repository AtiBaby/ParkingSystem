package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
@Getter
@Setter
public class Car {
    
    private String licensePlateNumber;
    private String brand;
    private String type;
    private String color;
    
    private Boolean isParking = false;
    private String parkingPlace;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public static List<Car> cars = new ArrayList<Car>();

    public Car(String license_plate_number, String brand, String type, String color) {
        this.licensePlateNumber = license_plate_number;
        this.brand = brand;
        this.type = type;
        this.color = color;
    }

    public Car() {
    }
    
    public String getLicense_plate_number() {
        return licensePlateNumber;
    }

    public void setLicense_plate_number(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }
    
    public String getFormattedStartTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
        String formattedDateTime = this.startTime.format(formatter);
        return formattedDateTime;
    }
    
    public String getFormattedEndTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
        String formattedDateTime = this.endTime.format(formatter);
        return formattedDateTime;
    }
}
