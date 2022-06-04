package ru.spbu.distolymp.entity.converter;

import ru.spbu.distolymp.entity.enumeration.Accessible;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladislav Konovalov
 */
@Converter
public class BooleanToAccessibleConverter implements AttributeConverter<Boolean, Accessible> {

    @Override
    public Accessible convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean == null) return null;
        return aBoolean ? Accessible.yes : Accessible.no;
    }

    @Override
    public Boolean convertToEntityAttribute(Accessible accessible) {
        return accessible == Accessible.yes;
    }

}
