package com.att.tdp.popcorn_palace.dto.bookingdto;


import com.att.tdp.popcorn_palace.dto.seatdto.SeatDTO;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDTO implements Serializable {

    @NotNull(message = ValidationMessages.SHOWTIME_REQUIRED)
    private Long showtimeId;

    @NotNull(message = ValidationMessages.USER_ID_REQUIRED)
    private Long userId;

    @NotBlank(message = ValidationMessages.USER_NAME_REQUIRED)
    private String userName;

    @Size(min = validationVariables.MIN_SEAT_NUMBER)
    private List<@Valid SeatDTO> seats;

}
