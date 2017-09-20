package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by holhosa on 2017.09.19..
 */
@Getter
@Setter
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name="ADDRESS_ID", unique=true, nullable=false)
    private int addressId;

    @Column(name="COUNTRY", nullable=false, length=100)
    private String country;

    @Column(name="ZIP", nullable=false, length=10)
    private String zip;

    @Column(name="CITY", nullable=false, length=100)
    private String city;

    @Column(name="STREET", nullable=false, length=100)
    private String street;

    @Column(name="STR_NUMBER", nullable=false)
    private Integer strNumber;

    @Override
    public String toString() {
        return country + ", " + zip + " " + city + ", " + street + " " + strNumber + ".";
    }
}
