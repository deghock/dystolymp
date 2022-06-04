package ru.spbu.distolymp.entity.division;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "settings")
public class Settings {

    @EmbeddedId
    private SettingsId id;

    @NotNull
    @Size(max = 255)
    @Column(name = "`value`")
    private String value;

}
