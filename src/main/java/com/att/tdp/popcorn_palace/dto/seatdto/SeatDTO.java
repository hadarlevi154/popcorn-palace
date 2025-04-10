package com.att.tdp.popcorn_palace.dto.seatdto;


import com.att.tdp.popcorn_palace.enums.SeatType;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO implements Serializable {

    @NotNull(message = ValidationMessages.SEAT_ID_REQUIRED)
    private Long seatId;

    @NotBlank(message = ValidationMessages.SEAT_NUMBER_REQUIRED)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ValidationMessages.SEAT_TYPE_REQUIRED)
    private SeatType seatType;

    @NotNull(message = ValidationMessages.PRICE_REQUIRED)
    @Positive(message = ValidationMessages.PRICE_POSITIVE)
    private Double price;


    public SeatDTO(@NotBlank Long seatId, @NotBlank String seatNumber, @NotNull @Positive Double price, @NotNull SeatType seatType) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.price = price;
    }

}
