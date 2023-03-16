package ru.spbu.distolymp.entity.geography;

import ru.spbu.distolymp.dto.entity.geography.town.TownDto;
import ru.spbu.distolymp.entity.education.School;
import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * @author Daria Usova
 */
@Entity
@Table(name = "towns")
public class Town {

    @Id
    @Column(name = "id_town")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_region", referencedColumnName = "id_region")
    private Region region;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible = Visible.yes;

    @Column(name = "editing")
    private boolean editing = false;

    @OneToMany(mappedBy = "town")
    private List<School> schools;

    public TownDto toDto() {
        TownDto dto = new TownDto();
        dto.setId(id);
        dto.setName(name);
        dto.setVisible(visible == Visible.yes);
        dto.setEditing(editing);
        return dto;
    }

    @Override
    public String toString() {
        return "Town{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", visible=" + visible +
                ", editing=" + editing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return Objects.equals(id, town.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visible getVisible() {
        return visible;
    }

    public void setVisible(Visible visible) {
        this.visible = visible;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

}
