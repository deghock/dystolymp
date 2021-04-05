package ru.spbu.distolymp.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladislav Konovalov
 */
@Converter
public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean == null) return null;
        return aBoolean ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer integer) {
        return integer == 1;
    }

}
