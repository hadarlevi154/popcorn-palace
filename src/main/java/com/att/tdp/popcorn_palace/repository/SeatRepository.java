package com.att.tdp.popcorn_palace.repository;


import com.att.tdp.popcorn_palace.entity.Seat;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Get available seats for a showtime
    List<Seat> findByShowtime_ShowtimeIdAndIsAvailableTrue(Long showtimeId);

    // Get all seats for a booking
    List<Seat> findByBooking_BookingId(Long bookingId);

    // Lock the selected seats by their seatIds (using pessimistic write locking)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(SQLQueries.SQL_FIND_AVAILABLE_SEATS)
    List<Seat> findSeatsForBooking(@Param("seatIds") List<Long> seatIds);

    // Lock the seat for updating (pessimistic lock)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(SQLQueries.SQL_FIND_SEATS_BY_SHOWTIME_SEATNUMBER)
    List<Seat> findAndLockSeatsByShowtimeAndSeatNumbers(
            @Param("showtimeId") Long showtimeId,
            @Param("seatNumbers") List<String> seatNumbers);
}
