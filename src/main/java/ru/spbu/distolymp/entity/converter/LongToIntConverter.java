package ru.spbu.distolymp.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladislav Konovalov
 */
@Converter
public class LongToIntConverter implements AttributeConverter<Long, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Long aLong) {
        if (aLong == null || aLong > Integer.MAX_VALUE) {
            return null;
        } else {
            return aLong.intValue();
        }
    }

    @Override
    public Long convertToEntityAttribute(Integer aInteger) {
        if (aInteger == null) {
            return null;
        } else {
            return Long.valueOf(aInteger);
        }
    }

}
