package ru.spbu.distolymp.entity.logs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import ru.spbu.distolymp.entity.enumeration.Action;
import ru.spbu.distolymp.entity.groups.Group;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.others.Answer;
import ru.spbu.distolymp.entity.tasks.Problem;
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
@Table(name = "user_log")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "log_date")
    private Date date;

    @NotNull
    @Size(max = 15)
    @Column(name = "log_ip")
    private String ip;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action", columnDefinition =
            "ENUM('login', 'logout', 'entry', 'exit', 'params', 'answer', 'start')", nullable = false)
    private Action action;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_group", referencedColumnName = "id_group")
    private Group group;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private Listing listing;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_problem", referencedColumnName = "id_problem")
    private Problem problem;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_answer", referencedColumnName = "id_answer")
    private Answer answer;

}
