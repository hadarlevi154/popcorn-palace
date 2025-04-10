package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.dto.showtimedto.*;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    // Create a new showtime
    @PostMapping
    public ResponseEntity<ShowtimeResponseDTO> createShowtime(
            @Valid @RequestBody CreateShowtimeDTO showtimeDTO) {

        ShowtimeResponseDTO created = showtimeService.createShowtime(showtimeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all showtimes
    @GetMapping
    public ResponseEntity<List<ShowtimeResponseDTO>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeService.getAllShowtimes());
    }

    // Get showtime by ID
    @GetMapping("/{id}")
    public ResponseEntity<ShowtimeResponseDTO> getShowtimeById(@PathVariable Long id) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(id));
    }

    // Update showtime
    @PutMapping("/{id}")
    public ResponseEntity<ShowtimeResponseDTO> updateShowtime(
            @PathVariable Long id,
            @Valid @RequestBody UpdateShowtimeDTO showtimeDTO) {

        ShowtimeResponseDTO updated = showtimeService.updateShowtime(id, showtimeDTO);
        return ResponseEntity.ok(updated);
    }

    // Delete showtime
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }
}

