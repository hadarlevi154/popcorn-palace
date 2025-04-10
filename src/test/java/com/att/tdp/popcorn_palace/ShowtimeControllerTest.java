package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.controller.ShowtimeController;
import com.att.tdp.popcorn_palace.dto.moviedto.MovieResponseDTO;
import com.att.tdp.popcorn_palace.dto.showtimedto.CreateShowtimeDTO;
import com.att.tdp.popcorn_palace.dto.showtimedto.ShowtimeResponseDTO;
import com.att.tdp.popcorn_palace.dto.showtimedto.UpdateShowtimeDTO;
import com.att.tdp.popcorn_palace.entity.Theater;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShowtimeControllerTest {

    @Mock
    private ShowtimeService showtimeService;

    @InjectMocks
    private ShowtimeController showtimeController;

    private MovieResponseDTO validMovieDTO;
    private Theater validTheater;
    private CreateShowtimeDTO validCreateShowtimeDTO;

    @BeforeEach
    public void setUp() {
        validMovieDTO = new MovieResponseDTO(1L, "The Dark Knight", "Action", 152, 9.0, 2008);
        validTheater = new Theater(1L, "IMAX", "NYC", 500);

        validCreateShowtimeDTO = new CreateShowtimeDTO(validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                LocalDateTime.of(2025, 4, 10, 20, 0), LocalDateTime.of(2025, 4, 10, 22, 0), 15.00);
    }

    @Test
    public void testCreateShowtime() {
        // Arrange
        ShowtimeResponseDTO newShowtime = new ShowtimeResponseDTO(1L, validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                validCreateShowtimeDTO.getStartTime(), validCreateShowtimeDTO.getEndTime(), validCreateShowtimeDTO.getPrice());

        when(showtimeService.createShowtime(Mockito.any(CreateShowtimeDTO.class))).thenReturn(newShowtime);

        // Act
        ResponseEntity<ShowtimeResponseDTO> response = showtimeController.createShowtime(validCreateShowtimeDTO);

        // Assert
        assertEquals(201, response.getStatusCodeValue()); // HTTP Created status
        assertEquals(newShowtime.getMovieId(), response.getBody().getMovieId());
        assertEquals(newShowtime.getTheaterId(), response.getBody().getTheaterId());
        assertEquals(newShowtime.getStartTime(), response.getBody().getStartTime());
        assertEquals(newShowtime.getEndTime(), response.getBody().getEndTime());
        assertEquals(newShowtime.getPrice(), response.getBody().getPrice());

        verify(showtimeService, times(1)).createShowtime(Mockito.any(CreateShowtimeDTO.class));
    }

    @Test
    public void testCreateShowtimeWithOverlappingCheck() {
        // Arrange
        ShowtimeResponseDTO newShowtime = new ShowtimeResponseDTO(1L, validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                validCreateShowtimeDTO.getStartTime(), validCreateShowtimeDTO.getEndTime(), validCreateShowtimeDTO.getPrice());

        when(showtimeService.createShowtime(Mockito.any(CreateShowtimeDTO.class)))
                .thenThrow(new IllegalArgumentException("No overlapping showtimes for the same theater"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            showtimeController.createShowtime(validCreateShowtimeDTO);
        });

        assertEquals("No overlapping showtimes for the same theater", exception.getMessage());
    }

    @Test
    public void testUpdateShowtime() {
        // Arrange
        Long showtimeId = 1L;
        UpdateShowtimeDTO validUpdateShowtimeDTO = new UpdateShowtimeDTO(validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                LocalDateTime.of(2025, 4, 10, 22, 0), LocalDateTime.of(2025, 4, 10, 23, 0), 20.00);

        ShowtimeResponseDTO updatedShowtime = new ShowtimeResponseDTO(showtimeId, validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                validUpdateShowtimeDTO.getStartTime(), validUpdateShowtimeDTO.getEndTime(), validUpdateShowtimeDTO.getPrice());

        when(showtimeService.updateShowtime(eq(showtimeId), Mockito.any(UpdateShowtimeDTO.class))).thenReturn(updatedShowtime);

        // Act
        ResponseEntity<ShowtimeResponseDTO> response = showtimeController.updateShowtime(showtimeId, validUpdateShowtimeDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // HTTP OK status
        assertEquals(updatedShowtime.getStartTime(), response.getBody().getStartTime());
        assertEquals(updatedShowtime.getEndTime(), response.getBody().getEndTime());
        assertEquals(updatedShowtime.getPrice(), response.getBody().getPrice());

        verify(showtimeService, times(1)).updateShowtime(eq(showtimeId), Mockito.any(UpdateShowtimeDTO.class));
    }

    @Test
    public void testDeleteShowtime() {
        // Arrange
        Long showtimeId = 1L;

        // Act
        ResponseEntity<Void> response = showtimeController.deleteShowtime(showtimeId);

        // Assert
        assertEquals(204, response.getStatusCodeValue()); // HTTP No Content status
        verify(showtimeService, times(1)).deleteShowtime(showtimeId);
    }

    @Test
    public void testGetShowtimeById() {
        // Arrange
        Long showtimeId = 1L;
        ShowtimeResponseDTO showtimeResponse = new ShowtimeResponseDTO(showtimeId, validMovieDTO.getMovieId(), validTheater.getTheaterId(),
                LocalDateTime.of(2025, 4, 10, 20, 0), LocalDateTime.of(2025, 4, 10, 22, 0), 15.00);

        when(showtimeService.getShowtimeById(showtimeId)).thenReturn(showtimeResponse);

        // Act
        ResponseEntity<ShowtimeResponseDTO> response = showtimeController.getShowtimeById(showtimeId);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // HTTP OK status
        assertEquals(showtimeResponse.getStartTime(), response.getBody().getStartTime());
        assertEquals(showtimeResponse.getEndTime(), response.getBody().getEndTime());
        assertEquals(showtimeResponse.getPrice(), response.getBody().getPrice());

        verify(showtimeService, times(1)).getShowtimeById(showtimeId);
    }



}

