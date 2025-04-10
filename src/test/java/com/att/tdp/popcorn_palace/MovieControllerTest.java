package com.att.tdp.popcorn_palace;

import com.att.tdp.popcorn_palace.controller.MovieController;
import com.att.tdp.popcorn_palace.dto.moviedto.CreateMovieDTO;
import com.att.tdp.popcorn_palace.dto.moviedto.UpdateMovieDTO;
import com.att.tdp.popcorn_palace.dto.moviedto.MovieResponseDTO;
import com.att.tdp.popcorn_palace.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private CreateMovieDTO movie1;
    private UpdateMovieDTO movie2;
    private MovieResponseDTO movie3;


    @BeforeEach
    public void setUp() {
        movie1 = new CreateMovieDTO("The Dark Knight", "Action", 152, 9.0, 2008);
        movie2 = new UpdateMovieDTO("Inception", "Sci-Fi", 148, 8.8, 2010);
        movie3 = new MovieResponseDTO(1L, "Aquaman", "Action", 120, 7.5, 2023);
    }

    @Test
    public void testCreateMovie() {
        // Arrange
        MovieResponseDTO createdMovieDTO = new MovieResponseDTO(1L, movie1.getTitle(), movie1.getGenre(),
                movie1.getDuration(), movie1.getRating(), movie1.getReleaseYear());

        // Mock the service to return a movie response when createMovie is called
        when(movieService.createMovie(movie1)).thenReturn(createdMovieDTO);

        // Act
        ResponseEntity<MovieResponseDTO> response = movieController.createMovie(movie1);

        // Assert
        assertEquals(201, response.getStatusCodeValue()); // HTTP Created status
        assertEquals(createdMovieDTO.getTitle(), response.getBody().getTitle());
        assertEquals(createdMovieDTO.getGenre(), response.getBody().getGenre());
        assertEquals(createdMovieDTO.getDuration(), response.getBody().getDuration());
        assertEquals(createdMovieDTO.getRating(), response.getBody().getRating());
        assertEquals(createdMovieDTO.getReleaseYear(), response.getBody().getReleaseYear());

        // Verify that the service's createMovie method was called exactly once
        verify(movieService, times(1)).createMovie(movie1);

    }

    @Test
    public void testUpdateMovie() {
        // Arrange
        Long movieId = 1L;
        MovieResponseDTO updatedMovieDTO = new MovieResponseDTO(movieId, movie2.getTitle(), movie2.getGenre(),
                movie2.getDuration(), movie2.getRating(), movie2.getReleaseYear());

        when(movieService.updateMovie(eq(movieId), Mockito.any(UpdateMovieDTO.class))).thenReturn(updatedMovieDTO);

        // Act
        ResponseEntity<MovieResponseDTO> response = movieController.updateMovie(movieId, movie2);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // HTTP OK status
        assertEquals(updatedMovieDTO.getTitle(), response.getBody().getTitle());
        assertEquals(updatedMovieDTO.getGenre(), response.getBody().getGenre());
        assertEquals(updatedMovieDTO.getDuration(), response.getBody().getDuration());
        assertEquals(updatedMovieDTO.getRating(), response.getBody().getRating());
        assertEquals(updatedMovieDTO.getReleaseYear(), response.getBody().getReleaseYear());

        verify(movieService, times(1)).updateMovie(eq(movieId), Mockito.any(UpdateMovieDTO.class));
    }

    @Test
    public void testGetMovieById() {
        // Arrange
        Long movieId = 1L;
        MovieResponseDTO movieResponse = new MovieResponseDTO(movieId, "Inception", "Sci-Fi", 148, 8.8, 2010);

        when(movieService.getMovieById(movieId)).thenReturn(movieResponse);

        // Act
        ResponseEntity<MovieResponseDTO> response = movieController.getMovieById(movieId);

        // Assert
        assertEquals(200, response.getStatusCodeValue()); // HTTP OK status
        assertEquals(movieResponse.getTitle(), response.getBody().getTitle());
        assertEquals(movieResponse.getGenre(), response.getBody().getGenre());
        assertEquals(movieResponse.getDuration(), response.getBody().getDuration());
        assertEquals(movieResponse.getRating(), response.getBody().getRating());
        assertEquals(movieResponse.getReleaseYear(), response.getBody().getReleaseYear());

        verify(movieService, times(1)).getMovieById(movieId);
    }

    @Test
    public void testDeleteMovie() {
        // Arrange
        Long movieId = 1L;

        // Act
        ResponseEntity <?> response = movieController.deleteMovie(movieId);

        // Assert
        assertEquals(204, response.getStatusCodeValue()); // HTTP No Content status
        verify(movieService, times(1)).deleteMovie(movieId);
    }

    @Test
    public void testGetAllMovies() {
        // Arrange
        MovieResponseDTO movieDTO = new MovieResponseDTO(movie3.getMovieId(), movie3.getTitle(), movie3.getGenre(), movie3.getDuration(), movie3.getRating(), movie3.getReleaseYear());
        List<MovieResponseDTO> movieList = List.of(movieDTO);

        // Mock the service call
        when(movieService.getAllMovies()).thenReturn(movieList);

        // Act
        ResponseEntity<List<MovieResponseDTO>> response = movieController.getAllMovies();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check if status is OK (200)
        assertEquals(1, response.getBody().size()); // Ensure there is one movie in the response body
        assertEquals("Aquaman", response.getBody().get(0).getTitle()); // Check if the movie title matches
        assertEquals("Action", response.getBody().get(0).getGenre()); // Check if the movie title matches
        assertEquals(2023, response.getBody().get(0).getReleaseYear()); // Check if the movie title matches

        // Verify the service method was called once
        verify(movieService, times(1)).getAllMovies();
    }
}
