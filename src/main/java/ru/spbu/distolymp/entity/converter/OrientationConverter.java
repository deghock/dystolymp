package ru.spbu.distolymp.entity.converter;

import ru.spbu.distolymp.entity.enumeration.Orientation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Daria Usova
 */
@Converter(autoApply = true)
public class OrientationConverter implements AttributeConverter<Orientation, String> {

    @Override
    public String convertToDatabaseColumn(Orientation orientation) {
        return orientation.getStringRepresentation();
    }

    @Override
    public Orientation convertToEntityAttribute(String stringRepresentation) {
        return Orientation.getOrientationByString(stringRepresentation);
    }

}
