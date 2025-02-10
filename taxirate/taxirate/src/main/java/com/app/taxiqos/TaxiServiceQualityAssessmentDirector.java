package com.app.taxiqos;

public class TaxiServiceQualityAssessmentDirector {

    // Метод для создания стандартной оценки качества сервиса агрегатора такси
    public void constructStandardQualityAssessment(TaxiServiceQualityAssessmentBuilder builder) {
        builder.setServiceName("Standard Taxi Service");
        builder.setRating(7.5f); // Рейтинг такси сервиса (например, по шкале от 1 до 10)
        builder.setQualityScore(80.0f); // Оценка качества сервиса (например, от 0 до 100)
        builder.setFeedbackDescription("This is a standard taxi service with average customer satisfaction.");
        builder.setAssessmentStatus("Pending"); // Статус оценки (например, 'Ожидает', 'Одобрена')
    }
}
