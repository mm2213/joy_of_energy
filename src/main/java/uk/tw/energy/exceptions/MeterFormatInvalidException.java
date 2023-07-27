package uk.tw.energy.exceptions;

public class MeterFormatInvalidException extends RuntimeException{
    public MeterFormatInvalidException() {
        super("error.meterReadings.wrongFormat");
    }
}
