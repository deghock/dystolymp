package ru.spbu.distolymp.entity.diploma;

import lombok.Data;
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
@Table(name = "diplom_types")
public class DiplomaType {

    @Id
    @Column(name = "diplom_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 45)
    @Column(name = "diplom_name")
    private String name;

    @NotNull
    @Column(name = "img")
    @Size(max = 45)
    private String imageFileName;

    @NotNull
    @Size(max = 150)
    @Column(name = "sub_title")
    private String subtitle;

    @Column(name = "print_image")
    private boolean printImage;

    @Column(name = "orientation")
    @Convert(converter = OrientationConverter.class)
    private Orientation orientation;

}