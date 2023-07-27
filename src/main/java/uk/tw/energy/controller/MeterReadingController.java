    package uk.tw.energy.controller;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import uk.tw.energy.commons.TwErrorCode;
    import uk.tw.energy.domain.ElectricityReading;
    import uk.tw.energy.domain.MeterReadings;
    import uk.tw.energy.exceptions.EnergyExceptions;
    import uk.tw.energy.exceptions.MeterFormatInvalidException;
    import uk.tw.energy.service.MeterReadingService;

    import java.util.List;
    import java.util.Optional;

    import static uk.tw.energy.commons.TwErrorCode.EMPTY_METER_READING;

    @RestController
    @RequestMapping("/readings")
    public class MeterReadingController {

        private final MeterReadingService meterReadingService;

        public MeterReadingController(MeterReadingService meterReadingService) {
            this.meterReadingService = meterReadingService;
        }

        // changed return type to Void for storeReadings and List<ElectricityReading> for readReadings
        @PostMapping("/store")
        public ResponseEntity<Void> storeReadings(@RequestBody MeterReadings meterReadings) {
            if (!meterReadingService.isMeterReadingsValid(meterReadings)) {
                throw new EnergyExceptions(TwErrorCode.EMPTY_METER_READING);
                //throw new BadRequestException();
                //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

//            if(!meterReadingService.isMeterFormatValid(meterReadings.smartMeterId())){
//                throw new MeterFormatInvalidException();
//            }

            meterReadingService.storeReadings(meterReadings.smartMeterId(), meterReadings.electricityReadings());
            return ResponseEntity.ok().build();
        }

        /*
        moved to class MeterReadingService

        private boolean isMeterReadingsValid(MeterReadings meterReadings) {
            String smartMeterId = meterReadings.smartMeterId();
            List<ElectricityReading> electricityReadings = meterReadings.electricityReadings();
            return smartMeterId != null && !smartMeterId.isEmpty()
                    && electricityReadings != null && !electricityReadings.isEmpty();
        }
         */

        @GetMapping("/read/{smartMeterId}")
        public ResponseEntity<List<ElectricityReading>> readReadings(@PathVariable String smartMeterId) {
            Optional<List<ElectricityReading>> readings = meterReadingService.getReadings(smartMeterId);

            return readings.isPresent()
                    ? ResponseEntity.ok(readings.get())
                    : ResponseEntity.notFound().build();
        }
    }
