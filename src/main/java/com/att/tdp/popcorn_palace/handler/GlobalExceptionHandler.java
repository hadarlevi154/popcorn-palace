package com.att.tdp.popcorn_palace.handler;

import com.att.tdp.popcorn_palace.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handle Validation Errors, creating ResponseEntity instead of buildErrorResponse
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors,
                "timestamp", LocalDateTime.now()
        ), HttpStatus.BAD_REQUEST);
    }

    // Handle overlapping showtimes for the same theater
    @ExceptionHandler(ShowtimeOverlapException.class)
    public ResponseEntity<Object> handleShowtimeOverlap(ShowtimeOverlapException ex) {
        // Create a response message
        String message = String.format("No overlapping showtimes allowed in the same theater. Please choose a different time. Overlaps with Showtime ID: %d", ex.getConflictingShowtimeId());

        // Use your buildErrorResponse method for consistency
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    // Handle no seats available
    @ExceptionHandler(seatNotAvailableException.class)
    public ResponseEntity<Object> handleSeatNotAvailableException(seatNotAvailableException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Handle all other uncaught exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
    }

    // Utility method to standardize error response
    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(Map.of(
                "status", status.value(),
                "message", message,
                "timestamp", LocalDateTime.now()
        ), status);
    }
}

