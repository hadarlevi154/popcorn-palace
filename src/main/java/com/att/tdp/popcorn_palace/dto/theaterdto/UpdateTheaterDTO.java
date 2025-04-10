package com.att.tdp.popcorn_palace.dto.theaterdto;

import com.att.tdp.popcorn_palace.util.AppConstants.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateTheaterDTO implements TheaterDTOBase, Serializable {

    @Size(min = validationVariables.MIN_NAME_LENGTH, max = validationVariables.MAX_NAME_LENGTH)
    private String theaterName;

    private String theaterLocation;

    @Positive(message = ValidationMessages.CAPACITY_POSITIVE)
    private Integer theaterCapacity;
}

