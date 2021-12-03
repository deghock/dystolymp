package ru.spbu.distolymp.exception;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Daria Usova
 */
@Log4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(Exception e) {
        return "exception/404";
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public String handleHttpRequestMethodNotSupportedException(Exception e) {
        log.error(e);
        return "exception/405";
    }
}
