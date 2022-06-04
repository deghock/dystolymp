package ru.spbu.distolymp.entity.education;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.converter.BooleanToVisibleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "school_types")
public class SchoolType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @Convert(converter = BooleanToVisibleConverter.class)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')")
    private boolean visible = false;

}
