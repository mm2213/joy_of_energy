package uk.tw.energy.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import uk.tw.energy.commons.TwErrorCode;

@Getter
public class EnergyExceptions extends RuntimeException{
    private TwErrorCode twErrorCode;
    public EnergyExceptions(TwErrorCode twErrorCode) {
        this.twErrorCode = twErrorCode;
    }

}
