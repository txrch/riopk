package com.app.taxirate;

import com.app.appUser.AppUser;
import com.app.appUser.UserService;
import com.app.enums.TaxiQualityAssessmentStatus;  // Новый статус для оценки качества
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.app.util.Global.saveFile;

@Service
@RequiredArgsConstructor
public class TaxiServiceQualityAssessmentService {

    private final TaxiServiceQualityAssessmentRepository repository;  // Репозиторий для анализа качества
    private final UserService userService;

    // Метод для получения всех анализов качества для текущего пользователя
    public List<TaxiServiceQualityAssessment> findAll() {
        List<TaxiServiceQualityAssessment> qualityAssessments;

        AppUser user = userService.getCurrentUser();

        if (Objects.requireNonNull(user.getRole()) == Role.CLIENT) {
            qualityAssessments = user.getOrderingsOwner();  // Все заказы владельца
        } else {
            qualityAssessments = new ArrayList<>();
        }

        qualityAssessments.sort(Comparator.comparing(TaxiServiceQualityAssessment::getId));
        Collections.reverse(qualityAssessments);

        return qualityAssessments;
    }

    // История анализов качества (для маркетинга, например)
    public List<TaxiServiceQualityAssessment> history() {
        return userService.getCurrentUser().getOrderingsMarketing();  // Анализы качества для маркетинга
    }

    // Метод для поиска анализа качества по ID
    public TaxiServiceQualityAssessment find(String id) {
        return repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ObjectNotFoundException("Не найден анализ по ИД: " + id));
    }

    // Метод для сохранения нового анализа качества
    public TaxiServiceQualityAssessment save(TaxiServiceQualityAssessment save, String type) {
        try {
            save.setType(TaxiServiceQualityAssessmentType.valueOf(type));  // Установка типа анализа
        } catch (Exception e) {
            throw new BadRequestException("Некорректный тип");
        }
        save.setOwner(userService.getCurrentUser());  // Установка владельца
        return repository.save(save);
    }

    // Метод для обновления файла отчета анализа качества
    public TaxiServiceQualityAssessment updateFile(String id, MultipartFile file) {
        TaxiServiceQualityAssessment qualityAssessment = find(id);
        try {
            qualityAssessment.setFile(saveFile(file, "taxiQualityAssessment"));  // Сохранение файла
        } catch (IOException e) {
            if (qualityAssessment.getFile().isEmpty()) repository.deleteById(qualityAssessment.getId());
            throw new BadRequestException("Некорректный файл");
        }
        return repository.save(qualityAssessment);
    }

    // Метод для сохранения анализа качества для теста
    public TaxiServiceQualityAssessment saveForTest(TaxiServiceQualityAssessment save) {
        return repository.save(save);
    }

    // Метод для обновления анализа качества для теста
    public TaxiServiceQualityAssessment updateForTest(String id, TaxiServiceQualityAssessment update) {
        TaxiServiceQualityAssessment old = find(id);
        old.updateForTest(update);
        return repository.save(old);
    }

    // Метод для удаления анализа качества для теста
    public void deleteForTest(String id) {
        repository.deleteById(find(id).getId());
    }

    // Метод для одобрения анализа качества (например, после завершения оценки)
    public TaxiServiceQualityAssessment approved(String id) {
        TaxiServiceQualityAssessment qualityAssessment = find(id);
        qualityAssessment.setStatus(TaxiQualityAssessmentStatus.APPROVED);  // Статус "Одобрено"
        qualityAssessment.setEvaluator(userService.getCurrentUser());  // Установка оценщика
        return repository.save(qualityAssessment);
    }

    // Метод для отклонения анализа качества
    public TaxiServiceQualityAssessment notApproved(String id) {
        TaxiServiceQualityAssessment qualityAssessment = find(id);
        qualityAssessment.setStatus(TaxiQualityAssessmentStatus.NOT_APPROVED);  // Статус "Не одобрено"
        qualityAssessment.setEvaluator(userService.getCurrentUser());  // Установка оценщика
        return repository.save(qualityAssessment);
    }
}