package ru.spbu.distolymp.dto.entity.geography.town;

import java.util.Objects;

/**
 * @author Vladislav Konovalov
 */
public class TownDto {

    private Long id;
    private String name;
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
        TownDto townDto = (TownDto) o;
        return visible == townDto.visible &&
                editing == townDto.editing &&
                Objects.equals(id, townDto.id) &&
                Objects.equals(name, townDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, visible, editing);
    }

}
