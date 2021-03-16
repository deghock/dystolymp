package ru.spbu.distolymp.entity.userinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"userId", "placeId"})
@Embeddable
public class UserPlaceId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_place")
    private Long placeId;

}
