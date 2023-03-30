package ru.spbu.distolymp.entity.converter;

import lombok.extern.log4j.Log4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Converter
@Log4j
public class LongDateToStringConverter implements AttributeConverter<Date, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @Override
    public String convertToDatabaseColumn(Date date) {
        if (date == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        return dateFormat.format(date);
    }

    @Override
    public Date convertToEntityAttribute(String date) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(date);
        } catch (ParseException e) {
            log.error("Unable to convert String-format of date to Date-format of date", e);
            return null;
        }
    }

}
