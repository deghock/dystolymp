package ru.spbu.distolymp.entity.logs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.lists.List;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "user_log_list")
public class UserLogList {

    @EmbeddedId
    private UserLogListId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "listId")
    private List list;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_date")
    private Date logDate;

}
