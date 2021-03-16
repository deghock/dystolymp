package ru.spbu.distolymp.entity.geography;

import lombok.Data;
import lombok.ToString;
import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Daria Usova
 */
@Data
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "id_country")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible;

    @Column(name = "editing")
    private boolean editing;

    @ToString.Exclude
    @OneToMany(mappedBy = "country",
            cascade = CascadeType.ALL)
    private List<Region> regions;

}
