package com.att.tdp.popcorn_palace.dto.bookingdto;


import com.att.tdp.popcorn_palace.dto.seatdto.SeatDTO;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO implements Serializable {

    private Long bookingId;
    private Long showtimeId;
    private String userName;
    private int numOfTickets;
    private double totalPrice;
    private List<SeatDTO> seats;
    private LocalDateTime purchaseTime;
}

