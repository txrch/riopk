package com.app.taxirate.converter;

import com.app.taxirate.TaxiServiceQualityAssessment;
import com.app.taxirate.TaxiServiceQualityAssessmentDto;
import com.app.taxiqos.converter.PolicyToPolicyDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter
        implements Converter<TaxiServiceQualityAssessment, TaxiServiceQualityAssessmentDto> {

    private final PolicyToPolicyDtoConverter policyToPolicyDtoConverter;

    @Override
    public TaxiServiceQualityAssessmentDto convert(TaxiServiceQualityAssessment source) {
        return new TaxiServiceQualityAssessmentDto(
                source.getId(),

                source.getAggregatorName(), // Имя агрегатора такси
                source.getAssessmentDate(), // Дата оценки

                source.getReportFile(),     // Файл отчета

                source.getAssessmentType().name(), // Тип оценки
                source.getAssessmentType().getName(), // Описание типа оценки

                source.getAssessmentStatus().name(), // Статус оценки
                source.getAssessmentStatus().getName(), // Описание статуса

                source.getRelatedPolicies().stream()
                        .map(policyToPolicyDtoConverter::convert)
                        .collect(Collectors.toList()) // Политики, связанные с оценкой
        );
    }
}
