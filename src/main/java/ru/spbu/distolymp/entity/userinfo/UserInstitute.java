package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.education.Institute;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "instituteId")
    private Institute institute;

    @NotNull
    @Size(max = 11)
    @Column(name = "`order`")
    private Integer order;

}
