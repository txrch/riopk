package com.app.appUser.profile;

import com.app.appUser.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiServiceQualityProfileService {

    private final TaxiServiceQualityProfileRepository repository;
    private final UserService userService;

    public TaxiServiceQualityProfile update(TaxiServiceQualityProfile update) {
        TaxiServiceQualityProfile old = userService.getCurrentUser().getTaxiServiceQualityProfile();
        old.update(update);
        return repository.save(old);
    }
}
