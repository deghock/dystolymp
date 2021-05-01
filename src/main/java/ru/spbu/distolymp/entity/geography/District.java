package ru.spbu.distolymp.entity.geography;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Maxim Andreev
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "districts")
public class District {

    @Id
    @Column(name = "id_district")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_town", referencedColumnName = "id_town")
    private Town town;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String name;

}
