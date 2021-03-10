package ru.spbu.distolymp.entity.education;

import lombok.Data;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.lists.Division;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Daria Usova
 */
@Data
@Entity
@Table(name = "places")
public class Place {

    @Id
    @Column(name = "id_place")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible;

    @NotNull
    @Type(type = "text")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "`order`")
    private Integer order;

}
