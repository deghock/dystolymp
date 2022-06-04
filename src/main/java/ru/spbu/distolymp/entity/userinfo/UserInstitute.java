package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.education.Institute;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "user_institutes")
public class UserInstitute {

    @EmbeddedId
    private UserInstituteId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "instituteId")
    @JoinColumn(name = "id_inst", referencedColumnName = "id_inst")
    private Institute institute;

    @NotNull
    @Column(name = "`order`")
    private Integer order;

}
