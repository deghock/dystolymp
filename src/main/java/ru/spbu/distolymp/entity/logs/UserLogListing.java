package ru.spbu.distolymp.entity.logs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.users.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "user_log_list")
public class UserLogListing {

    @EmbeddedId
    private UserLogListingId id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "listingId")
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private Listing listing;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_date")
    private Date logDate;

}
