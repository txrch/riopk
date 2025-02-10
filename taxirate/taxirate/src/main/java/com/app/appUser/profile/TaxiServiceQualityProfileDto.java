package com.app.appUser.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record TaxiServiceQualityProfileDto(
        Long id,

        @Size(min = 1, max = 255, message = "serviceName is required length 1-255")
        @NotEmpty(message = "serviceName is required")
        String serviceName,

        @Size(min = 1, max = 255, message = "driverName is required length 1-255")
        @NotEmpty(message = "driverName is required")
        String driverName,

        @Size(min = 1, max = 255, message = "passengerEmail is required length 1-255")
        @NotEmpty(message = "passengerEmail is required")
        @Email(message = "passengerEmail is incorrect")
        String passengerEmail,

        @Size(min = 1, max = 255, message = "passengerPhone is required length 1-255")
        @NotEmpty(message = "passengerPhone is required")
        String passengerPhone,

        @Size(min = 1, max = 255, message = "tripRating is required length 1-255")
        @NotEmpty(message = "tripRating is required")
        String tripRating
) {
}
