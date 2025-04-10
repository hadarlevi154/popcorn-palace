package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.moviedto.CreateMovieDTO;
import com.att.tdp.popcorn_palace.dto.moviedto.MovieDTOBase;
import com.att.tdp.popcorn_palace.dto.moviedto.MovieResponseDTO;
import com.att.tdp.popcorn_palace.dto.moviedto.UpdateMovieDTO;
import com.att.tdp.popcorn_palace.entity.Movie;
import com.att.tdp.popcorn_palace.exception.*;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.util.AppConstants.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestMapping("/movies")
public class MovieService {

    private final MovieRepository movieRepository;
    private ShowtimeRepository showtimeRepository;

    public MovieService(MovieRepository movieRepository, ShowtimeRepository showtimeRepository) {

        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
    }

    // Get all movies
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Get movie by Id
    @GetMapping("/movies/{Id}")
    @Transactional(readOnly = true)
    public MovieResponseDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIE_NOT_FOUND + id));
        return mapToResponseDTO(movie);
    }

    // Create movie
    @Transactional
    public MovieResponseDTO createMovie(CreateMovieDTO movieDTO) {
        Movie movie = new Movie();
        assignMovieValues(movieDTO, movie);
        return mapToResponseDTO(movieRepository.save(movie));
    }

    // Update movie
    @Transactional
    public MovieResponseDTO updateMovie(Long id, UpdateMovieDTO movieDTO) {
        // Fetch existing movie
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIE_NOT_FOUND + id));

        // Copy updated values from DTO to entity
        assignMovieValues(movieDTO, movie);

        // Save updated entity to DB
        Movie updated = movieRepository.save(movie);

        // Map to response DTO and return
        return mapToResponseDTO(updated);
    }

    // Delete movie
    @Transactional
    public void deleteMovie(Long id) {
        // Check if movie exists
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.MOVIE_NOT_FOUND + id));

        showtimeRepository.deleteByMovieId(movie.getMovieId());

        movieRepository.delete(movie);
    }

    private MovieResponseDTO mapToResponseDTO(Movie movie) {
        return new MovieResponseDTO(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getDuration(),
                movie.getRating(),
                movie.getReleaseYear()
        );
    }

    private void assignMovieValues(MovieDTOBase movieDTO, Movie movie) {
        // Update only non-null fields
        if (movieDTO.getTitle() != null) {
            movie.setTitle(movieDTO.getTitle());
        }
        if (movieDTO.getGenre() != null) {
            movie.setGenre(movieDTO.getGenre());
        }
        if (movieDTO.getDuration() != null) {
            movie.setDuration(movieDTO.getDuration());
        }
        if (movieDTO.getRating() != null) {
            movie.setRating(movieDTO.getRating());
        }
        if (movieDTO.getReleaseYear() != null) {
            movie.setReleaseYear(movieDTO.getReleaseYear());
        }
    }
}

