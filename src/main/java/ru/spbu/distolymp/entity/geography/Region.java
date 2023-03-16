package ru.spbu.distolymp.entity.geography;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Daria Usova
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "id_region")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", referencedColumnName = "id_country")
    private Country country;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String name;

    @NotNull
    @Column(name = "code")
    @Size(max = 3)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible = Visible.yes;

    @Column(name = "editing")
    private boolean editing = true;

}
