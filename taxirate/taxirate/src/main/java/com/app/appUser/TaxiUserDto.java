package com.app.appUser;

public record TaxiUserDto(
        Long id,

        String username,
        String role,

        TaxiUserDto profile
) {
}
