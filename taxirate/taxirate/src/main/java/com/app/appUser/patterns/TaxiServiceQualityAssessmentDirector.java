package com.app.appUser.patterns;

class TaxiServiceQualityAssessmentDirector {
    public void constructBasicQualityAssessment(TaxiServiceQualityAssessmentBuilder builder) {
        builder.setAggregatorName("Такси Агрегатор");
        builder.setDriverName("Иван Иванов");
        builder.addQualityCriterion("Долгое время ожидания");
        builder.addQualityCriterion("Негативные отзывы");
        builder.addQualityCriterion("Низкое качество автомобиля");
        builder.setRatingScore(75);
    }
}
