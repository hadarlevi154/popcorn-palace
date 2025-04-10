package com.att.tdp.popcorn_palace.entity;


import com.att.tdp.popcorn_palace.enums.SeatType;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(
        name = Database.SEAT_TABLE,
        uniqueConstraints = @UniqueConstraint(columnNames = {"showtime_id", "seatNumber"})
)
@Data // Provides getters, setters, equals, hashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seatid")
    private Long seatId;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;

    @Column(name = "price")
    private Double price;

    @NotNull(message = ValidationMessages.IS_AVAILABLE_REQUIRED)
    @Column(name = "is_available")
    private Boolean isAvailable = validationVariables.DEFAULT_IS_SEAT_AVAILABLE;

    @ManyToOne(optional = true)
    @JoinColumn(name = "bookingid", nullable = true)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)  // Foreign key to Showtime
    private Showtime showtime;

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Seat(seatId=" + seatId +
                ", seatNumber='" + seatNumber + "'" +
                ", seatType=" + seatType +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", bookingId=" + (booking != null ? booking.getBookingId() : null) +
                ", showtimeId=" + (showtime != null ? showtime.getShowtimeId() : null) +
                ")";
    }

}

