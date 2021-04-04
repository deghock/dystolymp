package ru.spbu.distolymp.entity.converter;

import lombok.extern.log4j.Log4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Vladislav Konovalov
 */
@Converter
@Log4j
public class DateToStringConverter implements AttributeConverter<Date, String> {

    private static final String DATE_PATTERN = "dd.MM.yyyy";

    @Override
    public String convertToDatabaseColumn(Date date) {
        if (date == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(date);
    }

    @Override
    public Date convertToEntityAttribute(String s) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(s);
        } catch (ParseException e) {
            log.error("Unable to convert String-format of date to Date-format of date", e);
            return null;
        }
    }

}
