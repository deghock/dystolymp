package ru.spbu.distolymp.entity.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "managing_group")
public class ManagingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mgroup")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "group_name")
    private String name;

    @Size(max = 4)
    @Column(name = "year")
    private String year;

    @OneToMany(mappedBy = "mGroup")
    private java.util.List<UserManagingGroup> userList;

}
