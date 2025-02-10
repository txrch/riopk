package com.app.taxiqos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiServiceQualityPolicyRepository extends JpaRepository<TaxiServiceQualityPolicy, Long> {

}
