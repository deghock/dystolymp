package ru.spbu.distolymp.entity.users;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.enumeration.GroupAccess;
import ru.spbu.distolymp.entity.groups.Group;
import ru.spbu.distolymp.entity.groups.UserManagingGroup;
import ru.spbu.distolymp.entity.lists.Division;
import ru.spbu.distolymp.entity.lists.Grade;
import ru.spbu.distolymp.entity.logs.UserLogList;
import ru.spbu.distolymp.entity.userinfo.*;

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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "login")
    private String login;

    @NotNull
    @Size(max = 50)
    @Column(name = "password")
    private String password;

    @Size(max = 50)
    @Column(name = "mainpass")
    private String confirmPassword;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "group_access", columnDefinition = "ENUM('single', 'multiple')", nullable = false)
    private GroupAccess groupAccess;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date")
    private Date registrationDate;

    @NotNull
    @Size(max = 15)
    @Column(name = "reg_ip")
    private String registrationIp;

    @NotNull
    @Size(max = 11)
    @Column(name = "award_id")
    private Integer awardId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passport", referencedColumnName = "id_passport")
    private Passport passport;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contact", referencedColumnName = "id_contact")
    private Contacts contacts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_school", referencedColumnName = "id_school")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_class", referencedColumnName = "id_class")
    private Grade grade;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_info", referencedColumnName = "id_info")
    private Information information;

    @ManyToMany
    @JoinTable(name = "user_groups", joinColumns = @JoinColumn(name = "id_user"),
                                     inverseJoinColumns = @JoinColumn(name = "id_group"))
    private java.util.List<Group> groupList;

    @OneToMany(mappedBy = "user")
    private java.util.List<UserInstitute> instituteList;

    @OneToMany(mappedBy = "user")
    private java.util.List<UserLogList> listList;

    @OneToMany(mappedBy = "user")
    private java.util.List<UserPlace> placeList;

    @OneToMany(mappedBy = "user")
    private java.util.List<UserManagingGroup> mGroupList;

}
