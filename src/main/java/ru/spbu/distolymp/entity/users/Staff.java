package ru.spbu.distolymp.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.groups.Group;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.entity.lists.List;
import ru.spbu.distolymp.entity.userinfo.Contacts;
import ru.spbu.distolymp.entity.userinfo.Passport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_staff")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(max = 50)
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "level", columnDefinition = "CHAR")
    private char level;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passport", referencedColumnName = "id_passport")
    private Passport passport;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contact", referencedColumnName = "id_contact")
    private Contacts contacts;

    @ManyToMany
    @JoinTable(name = "staff_divisions", joinColumns = @JoinColumn(name = "id_staff"),
                                         inverseJoinColumns = @JoinColumn(name = "id_division"))
    private java.util.List<Division> divisionList;

    @ManyToMany
    @JoinTable(name = "staff_groups", joinColumns = @JoinColumn(name = "id_staff"),
                                      inverseJoinColumns = @JoinColumn(name = "id_group"))
    private java.util.List<Group> groupList;

    @ManyToMany
    @JoinTable(name = "staff_lists", joinColumns = @JoinColumn(name = "id_staff"),
                                     inverseJoinColumns = @JoinColumn(name = "id_list"))
    private java.util.List<List> listList;

}
