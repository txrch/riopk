package com.app.taxiqos.converter;

import com.app.taxiqos.TaxiServiceQualityAssessment;
import com.app.taxiqos.TaxiServiceQualityAssessmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxiServiceQualityAssessmentDtoToTaxiServiceQualityAssessmentConverter implements Converter<TaxiServiceQualityAssessmentDto, TaxiServiceQualityAssessment> {
    @Override
    public TaxiServiceQualityAssessment convert(TaxiServiceQualityAssessmentDto source) {
        return new TaxiServiceQualityAssessment(
                source.taxiServiceName(),
                source.rating(),
                source.waitingTime(),
                source.serviceQualityDescription(),
                source.carCondition(),
                source.driverBehavior()
        );
    }
}
