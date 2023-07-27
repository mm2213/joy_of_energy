package uk.tw.energy.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
        super("error.meterReadings.invalid");
    }
}
