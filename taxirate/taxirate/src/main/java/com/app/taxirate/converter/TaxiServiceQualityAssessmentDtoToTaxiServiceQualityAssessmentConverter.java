package com.app.taxirate.converter;

import com.app.taxirate.TaxiServiceQualityAssessment;
import com.app.taxirate.TaxiServiceQualityAssessmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaxiServiceQualityAssessmentDtoToTaxiServiceQualityAssessmentConverter
        implements Converter<TaxiServiceQualityAssessmentDto, TaxiServiceQualityAssessment> {

    @Override
    public TaxiServiceQualityAssessment convert(TaxiServiceQualityAssessmentDto source) {
        return new TaxiServiceQualityAssessment(
                source.aggregatorName(),  // Имя агрегатора такси
                source.assessmentDate(),  // Дата оценки
                source.reportFile(),      // Файл отчета
                source.assessmentType(),  // Тип оценки
                source.assessmentStatus() // Статус оценки
        );
    }
}
