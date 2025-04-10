package com.att.tdp.popcorn_palace.dto.showtimedto;


import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowtimeDTO implements ShowtimeDTOBase, Serializable {

    @NotNull(message = ValidationMessages.MOVIE_ID_REQUIRED)
    private Long movieId;

    @NotNull(message = ValidationMessages.THEATER_ID_REQUIRED)
    private Long theaterId;

    @NotNull(message = ValidationMessages.START_TIME_REQUIRED)
    @FutureOrPresent(message = ValidationMessages.TIME_FUTURE_OR_PRESENT)
    private LocalDateTime startTime;

    @NotNull(message = ValidationMessages.END_TIME_REQUIRED)
    @Future(message = ValidationMessages.TIME_FUTURE)
    private LocalDateTime endTime;

    @NotNull(message = ValidationMessages.PRICE_REQUIRED)
    @Positive(message = ValidationMessages.PRICE_POSITIVE)
    private Double price;
}

