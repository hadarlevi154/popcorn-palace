package com.att.tdp.popcorn_palace.dto.showtimedto;


import java.time.LocalDateTime;

public interface ShowtimeDTOBase {
    Long getMovieId();
    Long getTheaterId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    Double getPrice();
}
