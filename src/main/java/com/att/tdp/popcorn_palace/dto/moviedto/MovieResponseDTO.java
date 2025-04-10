package com.att.tdp.popcorn_palace.dto.moviedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponseDTO implements MovieDTOBase, Serializable {
    private Long movieId;
    private String title;
    private String genre;
    private Integer duration;
    private Double rating;
    private Integer releaseYear;
}
