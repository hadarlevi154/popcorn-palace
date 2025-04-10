package com.att.tdp.popcorn_palace.entity;

import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Database.MOVIE_TABLE)
@Data // Provides getters, setters, equals, hashCode , etc.
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieid")
    private Long movieId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name ="genre", nullable = false)
    private String genre;

    @Column(name ="duration", nullable = false)
    private Integer duration; // in minutes

    @Column(name ="rating", nullable = false)
    private Double rating;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Override
    public String toString() {
        return "Movie(" +
                "movieId=" + movieId +
                ", title='" + title + "'" +
                ", genre='" + genre + "'" +
                ", duration=" + duration +
                ", rating=" + rating +
                ", releaseYear=" + releaseYear +
                ")";
    }
}



