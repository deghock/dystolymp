package ru.spbu.distolymp.entity.diploma;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.converter.OrientationConverter;
import ru.spbu.distolymp.entity.enumeration.Orientation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "diplom_types")
public class DiplomaType {

    @Id
    @Column(name = "diplom_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 45)
    @Column(name = "diplom_name")
    private String name = "диплом";

    @NotNull
    @Column(name = "img")
    @Size(max = 45)
    private String imageFileName;

    @NotNull
    @Size(max = 150)
    @Column(name = "sub_title")
    private String subtitle;

    @Column(name = "print_image")
    private boolean printImage = true;

    @Column(name = "orientation")
    @Convert(converter = OrientationConverter.class)
    private Orientation orientation;

}