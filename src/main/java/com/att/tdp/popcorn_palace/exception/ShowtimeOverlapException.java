package com.att.tdp.popcorn_palace.exception;

public class ShowtimeOverlapException extends RuntimeException {

    private final Long conflictingShowtimeId;

    public ShowtimeOverlapException(Long conflictingShowtimeId) {
        super("Showtime overlaps with an existing one in this theater. Please choose a different time. Overlaps with Showtime ID:" + conflictingShowtimeId);
        this.conflictingShowtimeId = conflictingShowtimeId;
    }

    public Long getConflictingShowtimeId() {
        return conflictingShowtimeId;
    }
}

