package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.dto.seatdto.*;
import com.att.tdp.popcorn_palace.service.SeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Get available seats for a given showtime
    @GetMapping("/showtimes/{showtimeid}/seats")
    public ResponseEntity<List<SeatDTO>> getAvailableSeatsByShow(@PathVariable Long showtimeid) {
        List<SeatDTO> availableSeats = seatService.getAvailableSeatsByShowtime(showtimeid);
        return ResponseEntity.ok(availableSeats);
    }

    // Get all seats by booking ID
    @GetMapping("/bookings/{bookingId}/seats")
    public List<SeatDTO> getSeatsByBooking(@PathVariable Long bookingId) {
        return seatService.getSeatsByBooking(bookingId);
    }
}

