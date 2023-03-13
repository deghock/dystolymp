package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.division.Division;
import ru.spbu.distolymp.entity.groups.Constraint;
import ru.spbu.distolymp.entity.logs.UserLogListing;
import ru.spbu.distolymp.entity.users.Staff;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "lists")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_list")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Type(type = "text")
    @Column(name = "`head`")
    private String head;

    @Type(type = "text")
    @Column(name = "foot")
    private String foot;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_constraint", referencedColumnName = "id_constraint")
    private Constraint constraint;

    @ManyToMany
    @JoinTable(name = "staff_lists", joinColumns = @JoinColumn(name = "id_list"),
                                     inverseJoinColumns = @JoinColumn(name = "id_staff"))
    private List<Staff> staffList;

    @OneToMany(mappedBy = "listing")
    private List<UserLogListing> userList;

    @OneToMany(mappedBy = "listing", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ListingProblems> problemList;

}
