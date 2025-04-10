package com.att.tdp.popcorn_palace.dto.moviedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class UpdateMovieDTO implements MovieDTOBase, Serializable {
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private Integer releaseYear;
}

