package com.app.taxiqos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TaxiServiceQualityAssessmentDto(
        Long id,

        @Size(min = 1, max = 255, message = "Service name is required, length 1-255")
        @NotEmpty(message = "Service name is required")
        String serviceName,

        String assessmentDate,

        int viewCount,

        @Min(value = 0, message = "Rating should be between 0 and 10")
        @Max(value = 10, message = "Rating should be between 0 and 10")
        float rating, // Рейтинг сервиса (например, от 1 до 10)

        @Min(value = 0, message = "Quality score is required min 0")
        @Max(value = 100, message = "Quality score is required max 100")
        float qualityScore, // Оценка качества (например, от 0 до 100)

        @Size(min = 1, max = 5000, message = "Description is required, length 1-5000")
        @NotEmpty(message = "Description is required")
        String feedbackDescription, // Описание и отзывы о сервисе

        String file, // Дополнительные файлы (например, отчет)

        String assessmentStatus, // Статус оценки (например, одобрена, отклонена)

        String statusName,

        Long serviceId // Идентификатор сервиса (такси агрегатора)
) {
}
