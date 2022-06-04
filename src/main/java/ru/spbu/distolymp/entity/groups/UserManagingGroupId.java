package ru.spbu.distolymp.entity.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"userId", "mGroupId"})
@Embeddable
public class UserManagingGroupId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_mgroup")
    private Long mGroupId;

}
