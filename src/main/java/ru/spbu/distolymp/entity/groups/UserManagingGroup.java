package ru.spbu.distolymp.entity.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "history_mgroup")
public class UserManagingGroup {

    @EmbeddedId
    private UserManagingGroupId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "mGroupId")
    @JoinColumn(name = "id_mgroup", referencedColumnName = "id_mgroup")
    private ManagingGroup mGroup;

    @Column(name = "`order`")
    private Integer order;

}
