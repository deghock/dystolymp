package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "contacts")
public class Contacts {

    @Id
    @Column(name = "id_contact")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 6)
    @Column(name = "postal_code")
    private String postalCode;

    @Size(max = 255)
    @Column(name = "country")
    private String country;

    @Size(max = 255)
    @Column(name = "town")
    private String town;

    @Size(max = 255)
    @Column(name = "street")
    private String street;

    @Size(max = 255)
    @Column(name = "building")
    private String building;

    @Size(max = 255)
    @Column(name = "apartment")
    private String apartment;

    @Size(max = 255)
    @Column(name = "mail")
    private String mail;

    @Size(max = 255)
    @Column(name = "cell_phone")
    private String cellPhone;

    @Size(max = 255)
    @Column(name = "home_phone")
    private String homePhone;

    @Size(max = 255)
    @Column(name = "work_phone")
    private String workPhone;

}
