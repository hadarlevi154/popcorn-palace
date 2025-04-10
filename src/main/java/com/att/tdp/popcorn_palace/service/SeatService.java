package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.seatdto.*;
import com.att.tdp.popcorn_palace.entity.Seat;
import com.att.tdp.popcorn_palace.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // Get available seats by showtime
    @Transactional(readOnly = true)
    public List<SeatDTO> getAvailableSeatsByShowtime(Long showtimeId) {
        return seatRepository.findByShowtime_ShowtimeIdAndIsAvailableTrue(showtimeId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Get seats by booking
    @Transactional(readOnly = true)
    public List<SeatDTO> getSeatsByBooking(Long bookingId) {
        return seatRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private SeatDTO mapToDTO(Seat seat) {
        return new SeatDTO(
                seat.getSeatId(),
                seat.getSeatNumber(),
                seat.getPrice(),
                seat.getSeatType()
        );
    }
}
