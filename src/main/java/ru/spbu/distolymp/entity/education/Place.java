package ru.spbu.distolymp.entity.education;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.enumeration.Visible;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.userinfo.UserPlace;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Daria Usova
 */
@Data
@EqualsAndHashCode(of = {"id"})
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
    private Visible visible = Visible.yes;

    @NotNull
    @Type(type = "text")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "`order`")
    private Integer order;

    @OneToMany(mappedBy = "place")
    private List<UserPlace> userList;

}
