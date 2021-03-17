package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "settings")
public class Settings {

    @EmbeddedId
    private SettingsId id;

    @NotNull
    @Size(max = 255)
    @Column(name = "`value`")
    private String value;

}
