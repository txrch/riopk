package com.app.taxiqos;

public interface TaxiServiceQualityAssessmentBuilder {
    void setTaxiServiceName(String taxiServiceName);  // Имя таксомоторного сервиса
    void setRating(float rating);  // Рейтинг сервиса (например, от 1 до 5)
    void setWaitingTime(float waitingTime);  // Время ожидания (в минутах)
    void setServiceQualityDescription(String description);  // Описание качества обслуживания
    void setCarCondition(String carCondition);  // Состояние автомобиля (например, "хорошее", "удовлетворительное")
    void setDriverBehavior(String driverBehavior);  // Поведение водителя
    TaxiServiceQualityAssessment build();  // Построить оценку качества
}
