package ru.spbu.distolymp.entity.education;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.userinfo.UserInstitute;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "institutes")
public class Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inst")
    private Long id;

    @NotNull
    @Type(type = "text")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "`order`")
    private Integer order;

    @OneToMany(mappedBy = "institute")
    private List<UserInstitute> userList;

}
