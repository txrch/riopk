package com.app.taxiqos;

import com.app.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TaxiServiceQualityAnalysisIntegrationTest {

    @Autowired
    private TaxiServiceQualityRepository repository;

    @Autowired
    private TaxiServiceQualityAnalysisService service;

    private TaxiServiceQuality taxiService1;
    private TaxiServiceQuality taxiService2;
    private TaxiServiceQuality taxiService3;

    @BeforeEach
    void setUp() {
        // Настроим объекты для тестирования
        taxiService1 = new TaxiServiceQuality(1L, "service1", "2025-01-01", 4.5f, "Good service", "file1");
        taxiService2 = new TaxiServiceQuality(2L, "service2", "2025-01-02", 3.0f, "Average service", "file2");
        taxiService3 = new TaxiServiceQuality(3L, "service3", "2025-01-03", 2.0f, "Poor service", "file3");

        repository.save(taxiService1);
        repository.save(taxiService2);
        repository.save(taxiService3);
    }

    @AfterEach
    void tearDown() {
        // Очистим базу данных после каждого теста
        repository.deleteAll();
    }

    @Test
    void findAllSuccess() {
        List<TaxiServiceQuality> actualServices = service.findAllForTest();

        assertThat(actualServices).hasSize(3);
    }

    @Test
    void findByIdSuccess() {
        TaxiServiceQuality foundService = service.find("1");

        assertThat(foundService.getId()).isEqualTo(1);
        assertThat(foundService.getName()).isEqualTo(taxiService1.getName());
        assertThat(foundService.getRating()).isEqualTo(taxiService1.getRating());
        assertThat(foundService.getFeedback()).isEqualTo(taxiService1.getFeedback());
        assertThat(foundService.getFile()).isEqualTo(taxiService1.getFile());
    }

    @Test
    void findByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.find("999"));
    }

    @Test
    void saveSuccess() {
        TaxiServiceQuality newService = new TaxiServiceQuality(null, "service4", "2025-01-04", 4.8f, "Excellent service", "file4");

        TaxiServiceQuality saved = service.saveForTest(newService);

        assertThat(saved.getName()).isEqualTo(newService.getName());
        assertThat(saved.getRating()).isEqualTo(newService.getRating());
        assertThat(saved.getFeedback()).isEqualTo(newService.getFeedback());
        assertThat(saved.getFile()).isEqualTo(newService.getFile());
    }

    @Test
    void updateSuccess() {
        TaxiServiceQuality update = new TaxiServiceQuality(1L, "updatedService", "2025-01-01", 5.0f, "Outstanding service", "updatedFile");

        TaxiServiceQuality updated = service.updateForTest("1", update);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getName()).isEqualTo(update.getName());
        assertThat(updated.getRating()).isEqualTo(update.getRating());
        assertThat(updated.getFeedback()).isEqualTo(update.getFeedback());
        assertThat(updated.getFile()).isEqualTo(update.getFile());
    }

    @Test
    void updateNotFound() {
        TaxiServiceQuality update = new TaxiServiceQuality(999L, "updatedService", "2025-01-01", 4.5f, "Good service", "updatedFile");

        assertThrows(ObjectNotFoundException.class, () -> service.updateForTest("999", update));
    }

    @Test
    void deleteSuccess() {
        service.deleteForTest("1");

        assertThrows(ObjectNotFoundException.class, () -> service.find("1"));
    }

    @Test
    void deleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.deleteForTest("999"));
    }
}
