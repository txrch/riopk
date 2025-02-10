package com.app.appUser.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiServiceQualityProfileRepository extends JpaRepository<TaxiServiceQualityProfile, Long> {
}
