package com.app.appUser.patterns;

interface TaxiServiceQualityAssessmentBuilder {
    void setAggregatorName(String aggregatorName);  // Название агрегатора такси
    void setDriverName(String driverName);          // Имя водителя
    void addQualityCriterion(String criterion);     // Добавление критерия оценки (например, комфорт, время ожидания)
    void setRatingScore(double score);              // Оценка качества услуг
    TaxiServiceQualityAssessment build();           // Создание итоговой оценки
}
