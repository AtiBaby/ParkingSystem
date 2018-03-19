package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by holhosa on 2017.09.19..
 */
@Getter
@Setter
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "COUNTRY", nullable = false, length = 50)
    private String country;

    @Column(name = "ZIP", nullable = false, length = 10)
    private String zip;

    @Column(name = "CITY", nullable = false, length = 50)
    private String city;

    @Column(name = "STREET", nullable = false, length = 50)
    private String street;

    @Column(name = "STR_NUMBER", nullable = false)
    private Integer strNumber;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private CarPark carPark;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public String toString() {
        return country + ", " + zip + " " + city + ", " + street + " " + strNumber + ".";
    }
}
