package com.app.appUser.converter;

import com.app.appUser.AppUser;
import com.app.appUser.TaxiUserDto;
import com.app.appUser.profile.converter.TaxiServiceQualityToDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserDtoConverter implements Converter<AppUser, TaxiUserDto> {

    private final TaxiServiceQualityToDtoConverter taxiServiceQualityToDtoConverter;

    @Override
    public TaxiUserDto convert(AppUser source) {
        return new TaxiUserDto(
                source.getId(),

                source.getUsername(),
                source.getRole().name(),

                taxiServiceQualityToDtoConverter.convert(source.getProfile())
        );
    }
}
