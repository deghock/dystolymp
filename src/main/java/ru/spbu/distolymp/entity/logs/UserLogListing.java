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
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "listingId")
    private Listing listing;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_date")
    private Date logDate;

}
