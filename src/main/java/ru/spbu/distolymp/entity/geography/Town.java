package ru.spbu.distolymp.entity.geography;

import lombok.Data;
import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@Entity
@Table(name = "towns")
public class Town {

    @Id
    @Column(name = "id_town")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_region", referencedColumnName = "id_region")
    private Region region;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible;

    @Column(name = "editing")
    private boolean editing;

}
