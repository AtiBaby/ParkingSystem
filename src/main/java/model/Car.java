package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Attila
 */
public class Car {
    
    private String license_plate_number;
    private String brand;
    private String type;
    private String color;
    
    private Boolean isParking = false;
    private String parkingPlace;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
    public static List<Car> cars = new ArrayList<Car>();

    public Car(String license_plate_number, String brand, String type, String color) {
        this.license_plate_number = license_plate_number;
        this.brand = brand;
        this.type = type;
        this.color = color;
    }

    public Car() {
    }
    
    public String getLicense_plate_number() {
        return license_plate_number;
    }

    public void setLicense_plate_number(String license_plate_number) {
        this.license_plate_number = license_plate_number;
    }
    
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getIsParking() {
        return isParking;
    }

    public void setIsParking(Boolean isParking) {
        this.isParking = isParking;
    }

    public String getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(String parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
