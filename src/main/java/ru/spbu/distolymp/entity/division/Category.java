package ru.spbu.distolymp.entity.division;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id_category")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

}
