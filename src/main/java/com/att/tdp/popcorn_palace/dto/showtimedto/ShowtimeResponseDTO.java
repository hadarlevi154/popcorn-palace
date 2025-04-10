package com.att.tdp.popcorn_palace.dto.showtimedto;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeResponseDTO implements Serializable {
    private Long showtimeId;
    private Long movieId;
    private Long theaterId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
}

