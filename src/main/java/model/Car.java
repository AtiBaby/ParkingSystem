package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.utils.Nation;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Attila
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NATION", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Nation nation;

    @Column(name = "LPN", nullable = false, length = 50)
    private String licensePlateNumber;
    @Column(name = "BRAND", length = 50)
    private String brand;
    @Column(name = "TYPE", length = 100)
    private String type;
    @Column(name = "COLOR", length = 50)
    private String color;

    @Column(name = "IS_PARKING")
    private Boolean isParking = false;

    @Column(name = "CAR_PARK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CarPark parkingPlace;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static List<Car> cars = new ArrayList<>();

    public String getFormattedStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
        String formattedDateTime = this.startTime.format(formatter);
        return formattedDateTime;
    }

    public String getFormattedEndTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");
        String formattedDateTime = this.endTime.format(formatter);
        return formattedDateTime;
    }
}
