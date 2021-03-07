package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@Entity
@Table(name = "divisions")
@EqualsAndHashCode(of = {"id"})
public class Division {

    @Id
    @Column(name = "id_division")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "prefix")
    private String prefix;

}
