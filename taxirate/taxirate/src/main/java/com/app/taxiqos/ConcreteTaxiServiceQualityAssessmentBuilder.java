package com.app.taxiqos;

public class ConcreteTaxiServiceQualityAssessmentBuilder implements TaxiServiceQualityAssessmentBuilder {
    private String taxiServiceName;
    private float rating;
    private float waitingTime;
    private String serviceQualityDescription;
    private String carCondition;
    private String driverBehavior;

    @Override
    public void setTaxiServiceName(String taxiServiceName) {
        this.taxiServiceName = taxiServiceName;
    }

    @Override
    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public void setWaitingTime(float waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public void setServiceQualityDescription(String description) {
        this.serviceQualityDescription = description;
    }

    @Override
    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }

    @Override
    public void setDriverBehavior(String driverBehavior) {
        this.driverBehavior = driverBehavior;
    }

    @Override
    public TaxiServiceQualityAssessment build() {
        return new TaxiServiceQualityAssessment(taxiServiceName, rating, waitingTime, serviceQualityDescription, carCondition, driverBehavior);
    }
}
