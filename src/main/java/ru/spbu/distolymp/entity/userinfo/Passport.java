package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.converter.DateToStringConverter;
import ru.spbu.distolymp.entity.enumeration.Sex;

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
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passport")
    private Long id;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Size(max = 255)
    @Column(name = "second_name")
    private String secondName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", columnDefinition = "ENUM('male', 'female')", nullable = false)
    private Sex sex = Sex.male;

    @Size(max = 10)
    @Convert(converter = DateToStringConverter.class)
    @Column(name = "birthday")
    private Date birthday;

    @Size(max = 255)
    @Column(name = "`where`")
    private String birthplace;

    @Size(max = 25)
    @Column(name = "n1")
    private String series;

    @Size(max = 25)
    @Column(name = "n2")
    private String number;

    @Type(type = "text")
    @Column(name = "`who`")
    private String who;

    @Size(max = 10)
    @Convert(converter = DateToStringConverter.class)
    @Column(name = "`when`")
    private Date when;

    @Size(max = 255)
    @Column(name = "code")
    private String code;

}
