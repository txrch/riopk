package com.app.appUser.patterns;

import java.util.ArrayList;
import java.util.List;

class ConcreteTaxiServiceQualityAssessmentBuilder implements TaxiServiceQualityAssessmentBuilder {
    private String tripId;
    private String aggregatorName;
    private List<String> qualityCriteria = new ArrayList<>();
    private double overallRating;

    @Override
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    @Override
    public void setAggregatorName(String aggregatorName) {
        this.aggregatorName = aggregatorName;
    }

    @Override
    public void addQualityCriterion(String criterion) {
        qualityCriteria.add(criterion);
    }

    @Override
    public void setOverallRating(double rating) {
        this.overallRating = rating;
    }

    @Override
    public TaxiServiceQualityAssessment build() {
        return new TaxiServiceQualityAssessment(tripId, aggregatorName, qualityCriteria, overallRating);
    }
}