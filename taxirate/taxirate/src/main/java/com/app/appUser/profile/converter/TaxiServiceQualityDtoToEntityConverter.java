package com.app.taxi.quality.converter;

import com.app.taxi.quality.TaxiServiceQuality;
import com.app.taxi.quality.TaxiServiceQualityDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxiServiceQualityDtoToEntityConverter implements Converter<TaxiServiceQualityDto, TaxiServiceQuality> {
    @Override
    public TaxiServiceQuality convert(TaxiServiceQualityDto source) {
        return new TaxiServiceQuality(
                source.serviceName(),
                source.driverName(),
                source.rideDate(),
                source.rating(),
                source.comments()
        );
    }
}
