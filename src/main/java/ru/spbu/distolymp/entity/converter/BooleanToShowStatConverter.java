package ru.spbu.distolymp.entity.converter;

import ru.spbu.distolymp.entity.enumeration.ShowStat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Vladislav Konovalov
 */
@Converter
public class BooleanToShowStatConverter implements AttributeConverter<Boolean, ShowStat> {

    @Override
    public ShowStat convertToDatabaseColumn(Boolean aBoolean) {
        if (aBoolean == null) return null;
        return aBoolean ? ShowStat.yes : ShowStat.no;
    }

    @Override
    public Boolean convertToEntityAttribute(ShowStat showStat) {
        return showStat == ShowStat.yes;
    }

}
