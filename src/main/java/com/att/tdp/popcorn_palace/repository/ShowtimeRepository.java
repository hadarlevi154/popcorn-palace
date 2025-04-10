package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entity.*;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Modifying
    @Transactional
    @Query(SQLQueries.SQL_DELETE_SHOWTIME_WHEN_DELETING_MOVIE)
    void deleteByMovieId(@Param("movieId") Long movieId);

    // Find showtime that overlaps with a given time range for a specific theater
    @Query(SQLQueries.SQL_FIND_OVERLAP_SHOWTIME)
    List<Showtime> findOverlappingShowtimes(
            @Param("theaterId") Long theaterId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludeId") Long excludeId
    );
}
