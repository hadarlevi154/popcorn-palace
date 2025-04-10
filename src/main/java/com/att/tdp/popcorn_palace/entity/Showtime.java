package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.att.tdp.popcorn_palace.util.AppConstants.*;


import java.time.LocalDateTime;

@Entity
@Table(name = Database.SHOWTIME_TABLE)
@Data // Provides getters, setters, equals, hashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtimeid")
    private Long showtimeId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "price")
    private Double price;

    @Override
    public String toString() {
        return "Showtime(showtimeId=" + showtimeId +
                ", movieId=" + (movie != null ? movie.getMovieId() : null) +
                ", movieTitle=" + (movie != null ? movie.getTitle() : null) +
                ", theaterId=" + (theater != null ? theater.getTheaterId() : null) +
                ", theaterName=" + (theater != null ? theater.getName() : null) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", price=" + price +
                ")";
    }

    public Long getId() {
        return showtimeId;
    }
}
