package ru.spbu.distolymp.entity.logs;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"userId", "listingId"})
@Embeddable
public class UserLogListingId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_list")
    private Long listingId;

}
