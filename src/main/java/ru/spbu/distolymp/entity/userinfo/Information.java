package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.enumeration.AbilityStatus;
import ru.spbu.distolymp.entity.enumeration.FamilyStatus;
import ru.spbu.distolymp.entity.enumeration.Residence;
import ru.spbu.distolymp.entity.groups.ManagingGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "informations")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_info")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "residence", columnDefinition = "ENUM('town', 'village')")
    private Residence residence;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ability", columnDefinition = "ENUM('usual', 'limited')", nullable = false)
    private AbilityStatus abilityStatus;

    @Column(name = "id_school_curator")
    private Long schoolCuratorId;

    @NotNull
    @Size(max = 255)
    @Column(name = "curator")
    private String curator;

    @NotNull
    @Size(max = 255)
    @Column(name = "curator_mail")
    private String curatorEmail;

    @NotNull
    @Size(max = 255)
    @Column(name = "school_mail")
    private String schoolEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "family", columnDefinition = "ENUM('usual', 'orphan')", nullable = false)
    private FamilyStatus familyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mgroup", referencedColumnName = "id_mgroup")
    private ManagingGroup managingGroup;

}
