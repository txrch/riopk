package com.app.appUser.patterns;
import java.util.List;

public class TaxiServiceQuality {
    private String tripId;
    private String passengerName;
    private List<String> qualityCriteria;
    private double overallRating;

    public TaxiServiceQuality(String tripId, String passengerName, List<String> qualityCriteria, double overallRating) {
        this.tripId = tripId;
        this.passengerName = passengerName;
        this.qualityCriteria = qualityCriteria;
        this.overallRating = overallRating;
    }

    public void showQualityDetails() {
        System.out.println("Поездка #" + tripId);
        System.out.println("Пассажир: " + passengerName);
        System.out.println("Критерии оценки: " + String.join(", ", qualityCriteria));
        System.out.println("Общая оценка: " + overallRating + " звёзд.");
    }
}
