package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by holhosa on 2018.03.19..
 */
@Getter
@Setter
public class User implements Serializable{

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "ADDRESS_ID")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Column(name = "PHONE_NUMBER", precision = 15)
    private Long phoneNumber;
}
