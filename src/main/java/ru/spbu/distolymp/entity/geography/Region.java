package ru.spbu.distolymp.entity.geography;

import ru.spbu.distolymp.dto.entity.geography.region.RegionDto;
import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Daria Usova
 */
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "id_region")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", referencedColumnName = "id_country")
    private Country country;

    @NotNull
    @Column(name = "name")
    @Size(max = 255)
    private String name;

    @NotNull
    @Column(name = "code")
    @Size(max = 3)
    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visible", columnDefinition = "ENUM('yes', 'no')", nullable = false)
    private Visible visible = Visible.yes;

    @Column(name = "editing")
    private boolean editing = true;

    public RegionDto toDto() {
        RegionDto regionDto = new RegionDto();
        regionDto.setId(id);
        regionDto.setName(name);
        regionDto.setCode(code);
        regionDto.setVisible(visible == Visible.yes);
        regionDto.setEditing(editing);
        return regionDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(id, region.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", visible=" + visible +
                ", editing=" + editing +
                '}';
    }

}
