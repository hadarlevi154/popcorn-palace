package com.att.tdp.popcorn_palace.dto.moviedto;

import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieDTO implements MovieDTOBase, Serializable {

    @Size(min = validationVariables.MIN_TITLE_LENGTH, max = validationVariables.MAX_TITLE_LENGTH)
    @NotBlank(message = ValidationMessages.TITLE_REQUIRED)
    private String title;

    @Size(min = validationVariables.MIN_GENRE_LENGTH, max = validationVariables.MAX_GENRE_LENGTH)
    @NotBlank(message = ValidationMessages.GENRE_REQUIRED)
    private String genre;

    @NotNull( message = ValidationMessages.DURATION_REQUIRED)
    @Positive(message = ValidationMessages.DURATION_POSITIVE)
    private Integer duration;

    @NotNull(message = ValidationMessages.RATING_REQUIRED)
    @Min(validationVariables.MIN_RATING)
    @Max(validationVariables.MAX_RATING)
    private Double rating;

    @NotNull( message = ValidationMessages.RELEASE_YEAR_REQUIRED)
    @Min(validationVariables.MIN_RELEASE_YEAR)
    @Max(validationVariables.MAX_RELEASE_YEAR)
    private Integer releaseYear;

}

