package com.app.appUser.profile.converter;

import com.app.appUser.patterns.TaxiServiceQuality;
import com.app.appUser.profile.TaxiServiceQualityController;
import com.app.appUser.profile.TaxiServiceQualityProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxiServiceQualityToDtoConverter implements Converter<TaxiServiceQuality, TaxiServiceQualityDto> {
    @Override
    public TaxiServiceQualityDto convert(TaxiServiceQuality source) {
        return new TaxiServiceQualityDto(
                source.getId(),
                source.getServiceName(),
                source.getDriverName(),
                source.getRideDate(),
                source.getRating(),
                source.getComments()
        );
    }
}
