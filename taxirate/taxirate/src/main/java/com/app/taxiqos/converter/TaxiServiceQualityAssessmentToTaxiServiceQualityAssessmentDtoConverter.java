package com.app.taxiqos.converter;

import com.app.taxiqos.TaxiServiceQualityAssessment;
import com.app.taxiqos.TaxiServiceQualityAssessmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter implements Converter<TaxiServiceQualityAssessment, TaxiServiceQualityAssessmentDto> {
    @Override
    public TaxiServiceQualityAssessmentDto convert(TaxiServiceQualityAssessment source) {
        return new TaxiServiceQualityAssessmentDto(
                source.getId(),

                source.getTaxiServiceName(),
                source.getRating(),
                source.getWaitingTime(),
                source.getServiceQualityDescription(),
                source.getCarCondition(),
                source.getDriverBehavior(),

                source.getStatus().name(),
                source.getStatus().getName(),

                source.getAssessmentDate()
        );
    }
}
