package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"divisionId", "key"})
@Embeddable
public class SettingsId implements Serializable {

    @Column(name = "id_division")
    private Long divisionId;

    @Column(name = "`key`")
    private String key;

}
