package ru.spbu.distolymp.entity.others;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.enumeration.Correctness;
import ru.spbu.distolymp.entity.tasks.ProblemType;
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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_answer")
    private Long id;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ans_date")
    private Date date;

    @NotNull
    @Size(max = 15)
    @Column(name = "ans_ip")
    private String ip;

    @Type(type = "text")
    @Column(name = "param")
    private String param;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "is_right", columnDefinition = "ENUM('Y', 'N', 'P', 'U', 'C')", nullable = false)
    private Correctness correctness = Correctness.U;

    @Size(max = 255)
    @Column(name = "grade")
    private String grade;

    @Temporal(TemporalType.TIME)
    @Column(name = "elapsed_time")
    private Date elapsedTime;

    @Type(type = "text")
    @Column(name = "answer")
    private String answerText;

    @Type(type = "text")
    @Column(name = "usernotes")
    private String userNotes;

    @Size(max = 255)
    @Column(name = "userfilename")
    private String userFileName;

    @Type(type = "text")
    @Column(name = "staffnotes")
    private String staffNotes;

    @Size(max = 255)
    @Column(name = "attempts")
    private String attempts;

    @Column(name = "points")
    private Double points;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_problem", referencedColumnName = "id_problem")
    private ProblemType problem;

}
