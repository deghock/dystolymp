package ru.spbu.distolymp.entity.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.enumeration.Accessible;
import ru.spbu.distolymp.entity.enumeration.ShowStat;
import ru.spbu.distolymp.entity.lists.Category;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.entity.lists.List;
import ru.spbu.distolymp.entity.users.Staff;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "`groups`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "accessible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Accessible accessible;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "show_stat", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private ShowStat showStat;

    @Size(max = 11)
    @Column(name = "priority")
    private Integer priority;

    @Type(type = "text")
    @Column(name = "info")
    private String info;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date")
    private Date registrationDate;

    @NotNull
    @Size(max = 11)
    @Column(name = "diplom_type")
    private Integer diplomaType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private List list;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_constraint", referencedColumnName = "id_constraint")
    private Constraint constraint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class", referencedColumnName = "id_class")
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_school", referencedColumnName = "id_school")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Category category;

    @ManyToMany
    @JoinTable(name = "staff_groups", joinColumns = @JoinColumn(name = "id_group"),
                                     inverseJoinColumns = @JoinColumn(name = "id_staff"))
    private java.util.List<Staff> staffList;

    @ManyToMany
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "id_group"),
                                     inverseJoinColumns = @JoinColumn(name = "id_user"))
    private java.util.List<User> userList;

}
