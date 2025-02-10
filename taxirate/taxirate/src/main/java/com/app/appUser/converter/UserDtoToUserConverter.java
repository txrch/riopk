package com.app.appUser.converter;

import com.app.appUser.AppUser;
import com.app.appUser.TaxiUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<TaxiUserDto, AppUser> {

    @Override
    public AppUser convert(TaxiUserDto source) {
        return new AppUser(
                source.username()
        );
    }
}
