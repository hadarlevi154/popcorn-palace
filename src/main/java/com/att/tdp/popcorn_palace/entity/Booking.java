package com.att.tdp.popcorn_palace.entity;

import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Database.BOOKING_TABLE)
@Data // Provides getters, setters, equals, hashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingid")
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = ValidationMessages.NUMBER_OF_TICKETS_REQUIRED)
    @Positive(message = ValidationMessages.NUMBER_OF_TICKETS_POSITIVE)
    @Column(name = "num_of_tickets")
    private int numOfTickets;

    @NotNull(message = ValidationMessages.TOTAL_PRICE_REQUIRED)
    @Positive(message = ValidationMessages.TOTAL_PRICE_POSITIVE)
    @Column(name = "total_price")
    private double totalPrice;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST, orphanRemoval = false)
    @Size(min = validationVariables.MIN_SEAT_NUMBER)
    private List<Seat> seats = new ArrayList<>();

    @NotNull(message = ValidationMessages.PURCHASE_TIME_REQUIRED)
    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @Override
    public String toString() {
        return "Booking(bookingId=" + bookingId +
                ", showtimeId=" + (showtime != null ? showtime.getShowtimeId() : null) +
                ", userId=" + (user != null ? user.getId() : null) +
                ", userName=" + userName +
                ", numOfTickets=" + numOfTickets +
                ", totalPrice=" + totalPrice +
                ", purchaseTime=" + purchaseTime +
                ", seatsCount=" + (seats != null ? seats.size() : 0) +
                ")";
    }


    public String getUserName() {
        return user.getUsername();
    }
}

