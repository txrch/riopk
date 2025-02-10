package com.app.taxirate;

import com.app.appUser.AppUser;
import com.app.enums.TaxiQualityAssessmentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TaxiServiceQualityAssessment implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "taxiqualityassessment_g")
    @SequenceGenerator(name = "taxiqualityassessment_g", sequenceName = "taxiqualityassessment_seq", allocationSize = 1)
    private Long id;

    private String serviceName;  // Название агрегатора такси (например, Uber, Yandex Go)
    private String assessmentDate;  // Дата проведения анализа качества

    @Column(length = 1000)
    private String reportFile = "";  // Отчет по качеству (может быть ссылкой на файл или описание)

    @Enumerated(EnumType.STRING)
    private TaxiQualityAssessmentStatus status = TaxiQualityAssessmentStatus.PENDING;  // Статус оценки качества

    @ManyToOne
    private AppUser owner;  // Владелец агрегатора такси
    @ManyToOne
    private AppUser evaluator;  // Сотрудник, проводящий анализ
    @OneToMany(mappedBy = "qualityAssessment", cascade = CascadeType.ALL)
    private List<TaxiServiceQualityFactor> qualityFactors = new ArrayList<>();  // Список факторов качества обслуживания

    public TaxiServiceQualityAssessment(String serviceName, String assessmentDate) {
        this.serviceName = serviceName;
        this.assessmentDate = assessmentDate;
    }

    public TaxiServiceQualityAssessment(Long id, String serviceName, String assessmentDate, String reportFile) {
        this.id = id;
        this.serviceName = serviceName;
        this.assessmentDate = assessmentDate;
        this.reportFile = reportFile;
    }

    public void updateForTest(TaxiServiceQualityAssessment update) {
        this.serviceName = update.getServiceName();
        this.assessmentDate = update.getAssessmentDate();
        this.reportFile = update.getReportFile();
    }

    public List<TaxiServiceQualityFactor> getQualityFactors() {
        qualityFactors.sort(Comparator.comparing(TaxiServiceQualityFactor::getId));
        Collections.reverse(qualityFactors);
        return qualityFactors;
    }
}