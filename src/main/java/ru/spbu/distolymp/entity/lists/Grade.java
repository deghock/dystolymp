package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.enumeration.RegistrationStatus;
import ru.spbu.distolymp.entity.users.Staff;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "classes")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "registration", columnDefinition = "ENUM('open', 'close')", nullable = false)
    private RegistrationStatus registrationStatus;

    @Size(max = 255)
    @Column(name = "codephrase")
    private String codePhrase;

    @Size(max = 255)
    @Column(name = "reply_to")
    private String replyTo;

    @Size(max = 255)
    @Column(name = "reply_name")
    private String replyName;

    @Size(max = 255)
    @Column(name = "service_mail")
    private String serviceEmail;

    @Type(type = "text")
    @Column(name = "before_text")
    private String beforeText;

    @Type(type = "text")
    @Column(name = "after_text")
    private String afterText;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_division", referencedColumnName = "id_division")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private Listing listing;

    @ManyToOne
    @JoinColumn(name = "id_staff", referencedColumnName = "id_staff")
    private Staff staff;

}
