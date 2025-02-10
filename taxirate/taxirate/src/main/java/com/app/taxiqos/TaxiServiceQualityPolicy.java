package com.app.taxiqos;

import com.app.appUser.TaxiServiceQualityUser;
import com.app.enums.TaxiServiceQualityStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static com.app.util.Global.getDateNow;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TaxiServiceQualityPolicy implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taxi_quality_policy_g")
    @SequenceGenerator(name = "taxi_quality_policy_g", sequenceName = "taxi_quality_policy_seq", allocationSize = 1)
    private Long id;

    private String policyName;
    private String date = getDateNow();
    private int viewCount = 0;
    private float serviceRating;
    private float costEfficiency;

    @Column(length = 5000)
    private String description;
    @Column(length = 1000)
    private String relatedFiles = "";

    @Enumerated(EnumType.STRING)
    private TaxiServiceQualityStatus status = TaxiServiceQualityStatus.PENDING;

    @ManyToOne
    private RiskAnalysis serviceRiskAnalysis;

    @ManyToOne
    private TaxiServiceQualityUser policyOwner;

    public TaxiServiceQualityPolicy(String policyName, float serviceRating, float costEfficiency, String description) {
        this.policyName = policyName;
        this.serviceRating = serviceRating;
        this.costEfficiency = costEfficiency;
        this.description = description;
    }

    public void update(TaxiServiceQualityPolicy update) {
        this.policyName = update.getPolicyName();
        this.description = update.getDescription();
        this.serviceRating = update.getServiceRating();
        this.costEfficiency = update.getCostEfficiency();
    }

    public static TaxiServiceQualityPolicy createWithBuilder(String policyName, float serviceRating, float costEfficiency, String description) {
        TaxiServiceQualityPolicyBuilder builder = new ConcreteTaxiServiceQualityPolicyBuilder();
        builder.setPolicyName(policyName);
        builder.setServiceRating(serviceRating);
        builder.setCostEfficiency(costEfficiency);
        builder.setDescription(description);
        return builder.build();
    }
}
