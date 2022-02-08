package ru.spbu.distolymp.dto.entity.geography.region;

import java.util.Objects;

/**
 * @author Daria Usova
 */
public class RegionDto {

    private Long id;
    private String name;
    private String code;
    private boolean visible;
    private boolean editing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
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
        RegionDto regionDto = (RegionDto) o;
        return visible == regionDto.visible && editing == regionDto.editing &&
                Objects.equals(id, regionDto.id) &&
                Objects.equals(name, regionDto.name) &&
                Objects.equals(code, regionDto.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, visible, editing);
    }

    @Override
    public String toString() {
        return "RegionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", visible=" + visible +
                ", editing=" + editing +
                '}';
    }

}
