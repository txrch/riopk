package com.app.appUser.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TaxiServiceQualityProfile implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taxi_service_quality_profile_g")
    @SequenceGenerator(name = "taxi_service_quality_profile_g", sequenceName = "taxi_service_quality_profile_seq", allocationSize = 1)
    private Long id;

    private String aggregatorName = "Такси Агрегатор";  // Название агрегатора
    private String driverName = "Имя водителя";  // Имя водителя
    private String carModel = "Модель автомобиля";  // Модель автомобиля
    private String customerFeedback = "Обратная связь";  // Обратная связь от клиента
    private String serviceRating = "Рейтинг сервиса";  // Оценка сервиса

    public TaxiServiceQualityProfile(String aggregatorName, String driverName, String carModel, String customerFeedback, String serviceRating) {
        this.aggregatorName = aggregatorName;
        this.driverName = driverName;
        this.carModel = carModel;
        this.customerFeedback = customerFeedback;
        this.serviceRating = serviceRating;
    }

    public void update(TaxiServiceQualityProfile update) {
        this.aggregatorName = update.getAggregatorName();
        this.driverName = update.getDriverName();
        this.carModel = update.getCarModel();
        this.customerFeedback = update.getCustomerFeedback();
        this.serviceRating = update.getServiceRating();
    }
}
