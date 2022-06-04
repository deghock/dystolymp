package ru.spbu.distolymp.entity.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "constraints")
public class Constraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constraint")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startdatetime")
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "enddatetime")
    private Date endDateTime;

    @Temporal(TemporalType.TIME)
    @Column(name = "duration")
    private Date duration;

}
