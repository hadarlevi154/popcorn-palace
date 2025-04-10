package com.att.tdp.popcorn_palace;


import com.att.tdp.popcorn_palace.controller.BookingController;
import com.att.tdp.popcorn_palace.dto.bookingdto.BookingResponseDTO;
import com.att.tdp.popcorn_palace.dto.bookingdto.CreateBookingDTO;
import com.att.tdp.popcorn_palace.dto.seatdto.SeatDTO;
import com.att.tdp.popcorn_palace.enums.SeatType;
import com.att.tdp.popcorn_palace.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private CreateBookingDTO validCreateBookingDTO;
    private BookingResponseDTO validBookingResponseDTO;



    @BeforeEach
    public void setUp() {
        // Set up valid seats
        SeatDTO seat1 = new SeatDTO(1L, "A1", 100.0, SeatType.REGULAR);
        SeatDTO seat2 = new SeatDTO(2L, "A2", 150.0, SeatType.VIP);

        // Set up valid CreateBookingDTO
        validCreateBookingDTO = new CreateBookingDTO(
                1L, // Show ID
                1L, // User ID
                "hadarlevi154",
                Arrays.asList(seat1, seat2) // Seat IDs to be booked
        );

        // Set up valid BookingResponseDTO
        validBookingResponseDTO = new BookingResponseDTO(
                1L, // Booking ID
                1L, // Show ID
                "hadarlevi154", // User ID
                2, // Number of tickets
                30.0, // Total price
                Arrays.asList(seat1, seat2), // Seat IDs
                LocalDateTime.of(2026, 4, 10, 20, 0) // Purchase time
        );
    }

    @Test
    public void testCreateBooking() {
        // Arrange: Mock service behavior
        when(bookingService.createBooking(Mockito.any(CreateBookingDTO.class))).thenReturn(validBookingResponseDTO);

        // Act: Call the controller
        ResponseEntity<BookingResponseDTO> response = bookingController.createBooking(validCreateBookingDTO);

        // Assert: Check if booking is successful
        assertEquals(201, response.getStatusCodeValue()); // HTTP Created status
        assertEquals(validBookingResponseDTO.getNumOfTickets(), response.getBody().getNumOfTickets());
        assertEquals(validBookingResponseDTO.getTotalPrice(), response.getBody().getTotalPrice());
        assertEquals(validBookingResponseDTO.getSeats().size(), response.getBody().getSeats().size());
        verify(bookingService, times(1)).createBooking(Mockito.any(CreateBookingDTO.class));
    }

    @Test
    public void testBookingWithAlreadyBookedSeats() {
        // Arrange: Mock service behavior to throw an exception for double booking
        when(bookingService.createBooking(Mockito.any(CreateBookingDTO.class)))
                .thenThrow(new IllegalArgumentException("Seat(s) already booked for this showtime"));

        // Act & Assert: Assert that the exception is thrown and the error is handled correctly
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingController.createBooking(validCreateBookingDTO);
        });

        // Assert: Ensure the exception message is correct
        assertEquals("Seat(s) already booked for this showtime", exception.getMessage());
        verify(bookingService, times(1)).createBooking(Mockito.any(CreateBookingDTO.class));
    }

}
