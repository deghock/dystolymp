package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.education.Place;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "user_places")
public class UserPlace {

    @EmbeddedId
    private UserPlaceId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "placeId")
    private Place place;

    @NotNull
    @Column(name = "`order`")
    private Integer order;

}
