package com.app.appUser.profile;

import com.app.appUser.profile.converter.TaxiServiceQualityDtoToEntityConverter;
import com.app.taxi.quality.converter.TaxiServiceQualityToDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static com.app.util.Global.CLIENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taxi-service/quality")
@Secured({CLIENT})
public class TaxiServiceQualityController {

    private final TaxiServiceQualityService service;
    private final TaxiServiceQualityToDtoConverter toDtoConverter;
    private final TaxiServiceQualityDtoToEntityConverter toConverter;

    @PutMapping
    public Result update(@Valid @RequestBody TaxiServiceQualityDto updateDto) {
        TaxiServiceQuality update = toConverter.convert(updateDto);
        TaxiServiceQuality updated = service.update(update);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success: Taxi service quality updated",
                toDtoConverter.convert(updated)
        );
    }

}
