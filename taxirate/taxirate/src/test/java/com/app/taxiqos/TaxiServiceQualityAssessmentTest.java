package com.app.taxiqos;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaxiServiceQualityAssessmentTest {

    @Mock
    private TaxiServiceQualityAssessmentRepository repository;

    @InjectMocks
    private TaxiServiceQualityAssessmentService service;

    List<TaxiServiceQualityAssessment> assessments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        assessments.add(new TaxiServiceQualityAssessment(1L, "TaxiAggregator1", "2025-02-10", 4.5f, "Good service overall", "file1"));
        assessments.add(new TaxiServiceQualityAssessment(2L, "TaxiAggregator2", "2025-02-11", 3.9f, "Moderate service", "file2"));
        assessments.add(new TaxiServiceQualityAssessment(3L, "TaxiAggregator3", "2025-02-12", 4.8f, "Excellent service", "file3"));
    }

    @AfterEach
    void tearDown() {
        assessments.clear();
    }

    @Test
    void findAllSuccess() {
        given(repository.findAll()).willReturn(assessments);

        List<TaxiServiceQualityAssessment> actualAssessments = service.findAllForTest();

        assertThat(actualAssessments.size()).isEqualTo(assessments.size());

        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        TaxiServiceQualityAssessment assessment = assessments.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(assessment));

        TaxiServiceQualityAssessment find = service.findForTest(1 + "");

        assertThat(find.getId()).isEqualTo(1);
        assertThat(find.getAggregatorName()).isEqualTo(assessment.getAggregatorName());
        assertThat(find.getDate()).isEqualTo(assessment.getDate());
        assertThat(find.getRating()).isEqualTo(assessment.getRating());
        assertThat(find.getComments()).isEqualTo(assessment.getComments());
        assertThat(find.getFile()).isEqualTo(assessment.getFile());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        given(repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.findForTest(1 + ""));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void saveSuccess() {
        TaxiServiceQualityAssessment save = assessments.get(0);

        given(repository.save(save)).willReturn(save);

        TaxiServiceQualityAssessment saved = service.saveForTest(save);

        assertThat(saved.getAggregatorName()).isEqualTo(save.getAggregatorName());
        assertThat(saved.getDate()).isEqualTo(save.getDate());
        assertThat(saved.getRating()).isEqualTo(save.getRating());
        assertThat(saved.getComments()).isEqualTo(save.getComments());
        assertThat(saved.getFile()).isEqualTo(save.getFile());

        verify(repository, times(1)).save(save);
    }

    @Test
    void updateSuccess() {
        TaxiServiceQualityAssessment old = assessments.get(0);
        TaxiServiceQualityAssessment update = assessments.get(1);

        given(repository.findById(1L)).willReturn(Optional.of(old));
        given(repository.save(old)).willReturn(old);

        TaxiServiceQualityAssessment updated = service.updateForTest(1 + "", update);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getAggregatorName()).isEqualTo(update.getAggregatorName());
        assertThat(updated.getDate()).isEqualTo(update.getDate());
        assertThat(updated.getRating()).isEqualTo(update.getRating());
        assertThat(updated.getComments()).isEqualTo(update.getComments());
        assertThat(updated.getFile()).isEqualTo(update.getFile());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(old);
    }

    @Test
    void updateNotFound() {
        TaxiServiceQualityAssessment update = assessments.get(1);

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest(1 + "", update));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deleteSuccess() {
        TaxiServiceQualityAssessment assessment = assessments.get(0);

        given(repository.findById(1L)).willReturn(Optional.of(assessment));
        doNothing().when(repository).deleteById(1L);

        service.deleteForTest(1 + "");

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.deleteForTest(1 + ""));

        verify(repository, times(1)).findById(1L);
    }
}
