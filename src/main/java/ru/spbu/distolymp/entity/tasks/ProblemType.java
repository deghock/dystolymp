package ru.spbu.distolymp.entity.tasks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.lists.Listing;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "p_type")
public class ProblemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_problem")
    private Long id;

    @Column(name = "type")
    private Integer type;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 255)
    @Column(name = "prefix")
    private String prefix;

    @NotNull
    @Column(name = "problemstatus")
    private Integer status;

    @OneToMany(mappedBy = "problem")
    private List<Listing> listingList;

}
