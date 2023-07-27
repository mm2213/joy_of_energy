package uk.tw.energy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.tw.energy.exceptions.BadRequestException;
import uk.tw.energy.exceptions.EnergyExceptions;
import uk.tw.energy.exceptions.MeterFormatInvalidException;
import uk.tw.energy.exceptions.NotFoundException;

import java.util.Locale;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {
    private final MessageSource messageSource;


    public ExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MeterFormatInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleMeterWrongFormatException(MeterFormatInvalidException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EnergyExceptions.class)
    public ResponseEntity<String> handleEnergyException(EnergyExceptions ex, Locale locale) {
        return ResponseEntity.status(ex.getTwErrorCode().getHttpStatus()).body(ex.getMessage());
    }
}
