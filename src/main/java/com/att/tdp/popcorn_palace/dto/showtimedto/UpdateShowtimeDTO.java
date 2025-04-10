package com.att.tdp.popcorn_palace.dto.showtimedto;


import com.att.tdp.popcorn_palace.util.AppConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateShowtimeDTO implements ShowtimeDTOBase, Serializable {

    private Long movieId;
    private Long theaterId;

    @FutureOrPresent(message = AppConstants.ValidationMessages.TIME_FUTURE_OR_PRESENT)
    private LocalDateTime startTime;

    @Future(message = AppConstants.ValidationMessages.TIME_FUTURE)
    private LocalDateTime endTime;

    @Positive(message = AppConstants.ValidationMessages.PRICE_POSITIVE)
    private Double price;
}

