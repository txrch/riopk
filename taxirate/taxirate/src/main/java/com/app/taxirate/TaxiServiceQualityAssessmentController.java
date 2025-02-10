package com.app.taxirate;

import com.app.taxirate.converter.TaxiServiceQualityAssessmentDtoToTaxiServiceQualityAssessmentConverter;
import com.app.taxirate.converter.TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static com.app.util.Global.CLIENT;
import static com.app.util.Global.POLICY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxi-assessments")
public class TaxiServiceQualityAssessmentController {

    private final TaxiServiceQualityAssessmentService service;
    private final TaxiServiceQualityAssessmentToTaxiServiceQualityAssessmentDtoConverter toDtoConverter;
    private final TaxiServiceQualityAssessmentDtoToTaxiServiceQualityAssessmentConverter toConverter;

    @GetMapping
    @Secured({CLIENT, POLICY})
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully retrieved all taxi service quality assessments",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/history")
    @Secured({POLICY})
    public Result history() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully retrieved history of taxi service quality assessments",
                service.history().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @PostMapping
    @Secured({CLIENT})
    public Result save(@Valid @RequestBody TaxiServiceQualityAssessmentDto saveDto, @RequestParam String type) {
        TaxiServiceQualityAssessment save = toConverter.convert(saveDto);
        TaxiServiceQualityAssessment saved = service.save(save, type);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully saved taxi service quality assessment",
                toDtoConverter.convert(saved)
        );
    }

    @PatchMapping("/{id}/file")
    @Secured({CLIENT})
    public Result updateFile(@PathVariable String id, @RequestParam MultipartFile file) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully updated taxi service quality assessment file",
                toDtoConverter.convert(service.updateFile(id, file))
        );
    }

    @GetMapping("/{id}/approved")
    @Secured({POLICY})
    public Result approved(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully approved taxi service quality assessment",
                toDtoConverter.convert(service.approved(id))
        );
    }

    @GetMapping("/{id}/not")
    @Secured({POLICY})
    public Result not(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Successfully rejected taxi service quality assessment",
                toDtoConverter.convert(service.notApproved(id))
        );
    }

}
