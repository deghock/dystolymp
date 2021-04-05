package ru.spbu.distolymp.entity.converter;

import ru.spbu.distolymp.entity.enumeration.Visible;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladislav Konovalov
 */
@Converter
public class BooleanToVisibleConverter implements AttributeConverter<Boolean, Visible> {

    @Override
    public Visible convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean == null) return null;
        return aBoolean ? Visible.yes : Visible.no;
    }

    @Override
    public Boolean convertToEntityAttribute(Visible visible) {
        return visible == Visible.yes;
    }

}
