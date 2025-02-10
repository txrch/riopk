package com.app.taxiqos;

import com.app.taxiqos.converter.TaxiServiceQualityAssessmentDtoToPolicyConverter;
import com.app.taxiqos.converter.TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxi-assessments")
public class TaxiServiceQualityAssessmentController {

    private final TaxiServiceQualityAssessmentService service;
    private final TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter toDtoConverter;
    private final TaxiServiceQualityAssessmentDtoToPolicyConverter toConverter;

    // Получение всех оценок качества
    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All Taxi Service Quality Assessments",
                service.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                        .map(toDtoConverter::convert)
                        .collect(Collectors.toList())
        );
    }

    // Получение оценок качества для текущего пользователя
    @GetMapping("/my")
    @Secured({POLICY})
    public Result my() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find My Taxi Service Quality Assessments",
                service.my().stream()
                        .map(toDtoConverter::convert)
                        .collect(Collectors.toList())
        );
    }

    // Получение конкретной оценки качества по ID
    @GetMapping("/{id}")
    @Secured({ADMIN, CLIENT})
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find Taxi Service Quality Assessment",
                toDtoConverter.convert(service.find(id))
        );
    }

    // Утверждение оценки качества
    @GetMapping("/{id}/approved")
    @Secured({ADMIN})
    public Result approved(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Approved Taxi Service Quality Assessment",
                toDtoConverter.convert(service.approved(id))
        );
    }

    // Ревизия оценки качества
    @GetMapping("/{id}/revision")
    @Secured({ADMIN})
    public Result revision(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Revision of Taxi Service Quality Assessment",
                toDtoConverter.convert(service.revision(id))
        );
    }

    // Ожидающая оценка качества
    @GetMapping("/{id}/waiting")
    @Secured({ADMIN})
    public Result waiting(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Waiting Taxi Service Quality Assessment",
                toDtoConverter.convert(service.waiting(id))
        );
    }

    // Создание новой оценки качества
    @PostMapping
    @Secured({POLICY})
    public Result save(@Valid @RequestBody TaxiServiceQualityAssessmentDto saveDto, @RequestParam String taxiServiceId) {
        TaxiServiceQualityAssessment save = toConverter.convert(saveDto);
        TaxiServiceQualityAssessment saved = service.save(save, taxiServiceId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save Taxi Service Quality Assessment",
                toDtoConverter.convert(saved)
        );
    }

    // Обновление существующей оценки качества
    @PutMapping("/{id}")
    @Secured({POLICY})
    public Result update(@PathVariable String id, @Valid @RequestBody TaxiServiceQualityAssessmentDto updateDto, @RequestParam String taxiServiceId) {
        TaxiServiceQualityAssessment update = toConverter.convert(updateDto);
        TaxiServiceQualityAssessment updated = service.update(id, update, taxiServiceId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Taxi Service Quality Assessment",
                toDtoConverter.convert(updated)
        );
    }

    // Обновление файла оценки качества
    @PatchMapping("/{id}/file")
    @Secured({POLICY})
    public Result updateFile(@PathVariable String id, @RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Taxi Service Quality Assessment File",
                toDtoConverter.convert(service.updateFile(id, files))
        );
    }

    // Удаление оценки качества
    @DeleteMapping("/{id}")
    @Secured({POLICY})
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete Taxi Service Quality Assessment"
        );
    }
}
