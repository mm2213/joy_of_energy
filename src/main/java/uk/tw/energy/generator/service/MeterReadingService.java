package uk.tw.energy.service;

import org.springframework.stereotype.Service;
import uk.tw.energy.domain.ElectricityReading;
import uk.tw.energy.domain.MeterReadings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MeterReadingService {

    private final Map<String, List<ElectricityReading>> meterAssociatedReadings;

    public MeterReadingService(Map<String, List<ElectricityReading>> meterAssociatedReadings) {
        this.meterAssociatedReadings = meterAssociatedReadings;
    }

    public Optional<List<ElectricityReading>> getReadings(String smartMeterId) {
        return Optional.ofNullable(meterAssociatedReadings.get(smartMeterId));
    }

    public void storeReadings(String smartMeterId, List<ElectricityReading> electricityReadings) {
//        if (!meterAssociatedReadings.containsKey(smartMeterId)) {
//            meterAssociatedReadings.put(smartMeterId, new ArrayList<>());
//        }
//        meterAssociatedReadings.get(smartMeterId).addAll(electricityReadings);

        meterAssociatedReadings.computeIfAbsent(smartMeterId, k -> new ArrayList<>()).addAll(electricityReadings);

    }

    public boolean isMeterReadingsValid(MeterReadings meterReadings) {
        String smartMeterId = meterReadings.smartMeterId();
        List<ElectricityReading> electricityReadings = meterReadings.electricityReadings();
        return smartMeterId != null && !smartMeterId.isEmpty()
                && electricityReadings != null && !electricityReadings.isEmpty();
    }

    public boolean isMeterFormatValid(String meterId){
        return meterId.matches("^smart-meter-\\d+$");
    }
}
