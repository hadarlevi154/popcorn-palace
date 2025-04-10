package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.theaterdto.*;
import com.att.tdp.popcorn_palace.entity.Theater;
import com.att.tdp.popcorn_palace.exception.ResourceNotFoundException;
import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    // Get all theaters
    @Transactional(readOnly = true)
    public List<TheaterResponseDTO> getAllTheaters() {
        return theaterRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get theater by ID
    @Transactional(readOnly = true)
    public TheaterResponseDTO getTheaterById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.THEATER_NOT_FOUND + id));
        return mapToResponseDTO(theater);
    }

    // Create a new theater
    @Transactional
    public TheaterResponseDTO createTheater(CreateTheaterDTO movieDTO) {
        Theater theater = new Theater();
        assignTheaterValues(movieDTO, theater);
        return mapToResponseDTO(theaterRepository.save(theater));
    }

    // Update a theater
    @Transactional
    public TheaterResponseDTO updateTheater(Long id, UpdateTheaterDTO movieDTO) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.THEATER_NOT_FOUND + id));
        assignTheaterValues(movieDTO, theater);
        return mapToResponseDTO(theaterRepository.save(theater));
    }

    // Delete a theater
    @Transactional
    public void deleteTheater(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.THEATER_NOT_FOUND + id));
        theaterRepository.delete(theater);
    }

    private void assignTheaterValues(TheaterDTOBase movieDTO, Theater theater) {

        if (movieDTO.getTheaterName() != null) {
            theater.setTheaterName(movieDTO.getTheaterName());
        }

        if (movieDTO.getTheaterLocation() != null) {
            theater.setTheaterLocation(movieDTO.getTheaterLocation());
        }

        if (movieDTO.getTheaterCapacity() != null) {
            theater.setTheaterCapacity(movieDTO.getTheaterCapacity());
        }
    }

    private TheaterResponseDTO mapToResponseDTO(Theater theater) {
        return new TheaterResponseDTO(
                theater.getTheaterId(),
                theater.getTheaterName(),
                theater.getTheaterLocation(),
                theater.getTheaterCapacity()
        );
    }
}
