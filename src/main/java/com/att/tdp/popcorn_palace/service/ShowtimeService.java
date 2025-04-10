package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.showtimedto.*;
import com.att.tdp.popcorn_palace.entity.*;
import com.att.tdp.popcorn_palace.exception.*;
import com.att.tdp.popcorn_palace.repository.*;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;

    public ShowtimeService(
            ShowtimeRepository showtimeRepository,
            MovieRepository movieRepository,
            TheaterRepository theaterRepository
    ) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
    }

    // Create showtime
    @Transactional
    public ShowtimeResponseDTO createShowtime(CreateShowtimeDTO showtimeDTO) {
        Movie movie = movieRepository.findById(showtimeDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIE_NOT_FOUND + showtimeDTO.getMovieId()));
        Theater theater = theaterRepository.findById(showtimeDTO.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.THEATER_NOT_FOUND + showtimeDTO.getTheaterId()));

        // Check for overlapping showtimes
        validateNoOverlap(theater.getTheaterId(), showtimeDTO.getStartTime(), showtimeDTO.getEndTime(), null);

        Showtime showtime = new Showtime();
        assignShowtimeValues(showtimeDTO, showtime);
        showtime.setMovie(movie);
        showtime.setTheater(theater);

        return mapToResponseDTO(showtimeRepository.save(showtime));
    }

    // Get all showtimes
    @Transactional(readOnly = true)
    public List<ShowtimeResponseDTO> getAllShowtimes() {
        return showtimeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get showtime by Id
    @Transactional(readOnly = true)
    public ShowtimeResponseDTO getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SHOWTIME_NOT_FOUND + id));
        return mapToResponseDTO(showtime);
    }

    // Update showtime
    @Transactional
    public ShowtimeResponseDTO updateShowtime(Long id, UpdateShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SHOWTIME_NOT_FOUND + id));

        Movie movie = movieRepository.findById(showtimeDTO.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIE_NOT_FOUND + showtimeDTO.getMovieId()));
        Theater theater = theaterRepository.findById(showtimeDTO.getTheaterId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.THEATER_NOT_FOUND + showtimeDTO.getTheaterId()));
        // Check for overlapping showtime (excluding current), only if updating start/end time
        if (showtimeDTO.getStartTime() != null || showtimeDTO.getEndTime() != null) {
            validateNoOverlap(theater.getTheaterId(), showtimeDTO.getStartTime(), showtimeDTO.getEndTime(), showtime.getShowtimeId());
        }

        assignShowtimeValues(showtimeDTO, showtime);
        showtime.setMovie(movie);
        showtime.setTheater(theater);

        return mapToResponseDTO(showtimeRepository.save(showtime));
    }

    // Delete showtime
    @Transactional
    public void deleteShowtime(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SHOWTIME_NOT_FOUND + id));
        showtimeRepository.delete(showtime);
    }

    // Shared mapping logic
    private void assignShowtimeValues(ShowtimeDTOBase showtimeDTO, Showtime showtime) {
        if (showtimeDTO.getStartTime() != null) {
            showtime.setStartTime(showtimeDTO.getStartTime());
        }
        if (showtimeDTO.getEndTime() != null) {
            showtime.setEndTime(showtimeDTO.getEndTime());
        }
        if (showtimeDTO.getPrice() != null) {
            showtime.setPrice(showtimeDTO.getPrice());
        }
    }

    private ShowtimeResponseDTO mapToResponseDTO(Showtime showtime) {
        return new ShowtimeResponseDTO(
                showtime.getShowtimeId(),
                showtime.getMovie().getMovieId(),
                showtime.getTheater().getTheaterId(),
                showtime.getStartTime(),
                showtime.getEndTime(),
                showtime.getPrice()
        );
    }

    // Validate no overlaps
    private void validateNoOverlap(Long theaterId, LocalDateTime startTime, LocalDateTime endTime, Long excludeIdIfAny) {
        List<Showtime> overlapping = showtimeRepository.findOverlappingShowtimes(theaterId, startTime, endTime, excludeIdIfAny);

        if (!overlapping.isEmpty()) {
            Long conflictingShowtimeId = overlapping.getFirst().getId(); // Get the first conflicting showtime ID
            throw new ShowtimeOverlapException(conflictingShowtimeId);
        }
    }
}
