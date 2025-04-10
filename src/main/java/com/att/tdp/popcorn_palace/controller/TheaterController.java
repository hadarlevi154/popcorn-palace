package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.theaterdto.*;
import com.att.tdp.popcorn_palace.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    // Create a new theater
    @PostMapping
    public ResponseEntity<TheaterResponseDTO> createTheater(
            @Valid @RequestBody CreateTheaterDTO theaterDTO) {

        TheaterResponseDTO created = theaterService.createTheater(theaterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Get all theaters
    @GetMapping
    public ResponseEntity<List<TheaterResponseDTO>> getAllTheaters() {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    // Get theater by ID
    @GetMapping("/{id}")
    public ResponseEntity<TheaterResponseDTO> getTheaterById(@PathVariable Long id) {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    // Update theater
    @PutMapping("/{id}")
    public ResponseEntity<TheaterResponseDTO> updateTheater(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTheaterDTO theaterDTO) {

        TheaterResponseDTO updated = theaterService.updateTheater(id, theaterDTO);
        return ResponseEntity.ok(updated);
    }

    // Delete theater
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}

