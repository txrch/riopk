package com.app.taxirate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiServiceQualityAssessmentRepository extends JpaRepository<TaxiServiceQualityAssessment, Long> {
}