package com.att.tdp.popcorn_palace.dto.theaterdto;

import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class CreateTheaterDTO implements TheaterDTOBase, Serializable {

    @NotBlank(message = ValidationMessages.NAME_REQUIRED)
    @Size(min = validationVariables.MIN_NAME_LENGTH, max = validationVariables.MAX_NAME_LENGTH)
    private String theaterName;

    @NotBlank(message = ValidationMessages.THEATER_LOCATION_REQUIRED)
    private String theaterLocation;

    @NotNull(message = ValidationMessages.THEATER_CAPACITY_REQUIRED)
    @Positive(message = ValidationMessages.THEATER_CAPACITY_POSITIVE)
    private Integer theaterCapacity;
}
