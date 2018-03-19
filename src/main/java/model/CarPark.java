package model;

import lombok.Getter;
import lombok.Setter;
import model.utils.CarParkType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by holhosa on 2017.09.19..
 */
@Getter
@Setter
public class CarPark implements Serializable {

    public static List<CarPark> carParks = new ArrayList<>();

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE", length = 30)
    @Enumerated(EnumType.STRING)
    private CarParkType type;

    @JoinColumn(name = "ADDRESS_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "PARKING_FEE", precision = 8)
    private Integer parkingFee;

    @Column(name = "SIZE", precision = 4)
    private Integer size;

    @Column(name = "X_COORD", precision = 20, scale = 18)
    private BigDecimal xcoordinate;

    @Column(name = "Y_COORD", precision = 20, scale = 18)
    private BigDecimal ycoordinate;
}
