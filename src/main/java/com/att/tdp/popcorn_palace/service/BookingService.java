package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.bookingdto.*;
import com.att.tdp.popcorn_palace.dto.seatdto.SeatDTO;
import com.att.tdp.popcorn_palace.entity.*;
import com.att.tdp.popcorn_palace.exception.*;
import com.att.tdp.popcorn_palace.repository.*;
import com.att.tdp.popcorn_palace.util.AppConstants.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    public BookingService(
            BookingRepository bookingRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    // Create booking
    @Transactional
    public BookingResponseDTO createBooking(CreateBookingDTO bookingDTO) {
        // Fetch showtime
        Showtime showtime = showtimeRepository.findById(bookingDTO.getShowtimeId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SHOWTIME_NOT_FOUND + bookingDTO.getShowtimeId()));

        // Fetch the User entity using the provided userName
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + bookingDTO.getUserId()));

        // Create a new booking entity
        Booking booking = new Booking();
        booking.setUser(user);  // Set the User object
        booking.setUserName(user.getUsername());  // Set the userName from the User entity
        booking.setShowtime(showtime);  // Set the Showtime object


        // Get the selected seat IDs from the request
        List<Long> seatIds = bookingDTO.getSeats().stream()
                .map(SeatDTO::getSeatId)  // Assuming SeatDTO has seatId field
                .collect(Collectors.toList());

        // Lock only the selected seats for booking (using pessimistic locking)
        List<Seat> seats = seatRepository.findSeatsForBooking(seatIds);

        // Check if all selected seats are available
        for (Seat seat : seats) {
            if (!seat.isAvailable()) {
                // If any seat is not available, throw an exception and abort the booking
                throw new seatNotAvailableException("Seat " + seat.getSeatNumber() + " is already booked. Please select other seats.");
            }
        }

        // Mark the seats as booked and associate them with the booking
        double totalPrice = 0.0;
        for (Seat seat : seats) {
            seat.setBooking(booking);
            seat.setIsAvailable(false);
            seat.setShowtime(showtime);
            seatRepository.save(seat);
            totalPrice += seat.getPrice();
            booking.getSeats().add(seat);
        }

        // If no seats are available
        if (booking.getSeats().isEmpty()) {
            throw new seatNotAvailableException("Could not book any seats. All requested seats are unavailable.");
        }

        // Add values to booking and save it
        booking.setNumOfTickets(seats.size());  // Number of tickets equals the number of selected seats
        booking.setTotalPrice(totalPrice);  // Total price based on seat prices
        booking.setPurchaseTime(LocalDateTime.now());
        booking = bookingRepository.save(booking);


        // Map and return the booking response DTO
        List<SeatDTO> seatDTOs = seats.stream()
                .map(seat -> new SeatDTO(seat.getSeatId(), seat.getSeatNumber(), seat.getPrice(), seat.getSeatType()))
                .collect(Collectors.toList());

        return new BookingResponseDTO(
                booking.getBookingId(),
                booking.getShowtime().getShowtimeId(),
                booking.getUser().getUsername(),
                booking.getNumOfTickets(),
                booking.getTotalPrice(),
                seatDTOs,
                booking.getPurchaseTime()
        );
    }

    // Get booking by Id
    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOKING_NOT_FOUND + id));
        return mapToResponseDTO(booking);
    }

    // Get all bookings
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Update booking
    @Transactional
    public BookingResponseDTO updateBooking(Long id, CreateBookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOKING_NOT_FOUND + id));

        Showtime showtime = showtimeRepository.findById(bookingDTO.getShowtimeId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.SHOWTIME_NOT_FOUND + bookingDTO.getShowtimeId()));
        booking.setShowtime(showtime);

        // Build updated seat list
        List<String> incomingSeatNumbers = bookingDTO.getSeats().stream()
                .map(SeatDTO::getSeatNumber)
                .toList();

        // Step 1: Lock the seats being added to the booking
        List<Seat> allLockedSeats = seatRepository.findAndLockSeatsByShowtimeAndSeatNumbers(
                showtime.getShowtimeId(), incomingSeatNumbers);

        // Add new seats
        for (SeatDTO seatDTO : bookingDTO.getSeats()) {
            boolean alreadyInBooking = booking.getSeats().stream()
                    .anyMatch(existing -> existing.getSeatNumber().equals(seatDTO.getSeatNumber()));

            if (!alreadyInBooking) {
                Optional<Seat> optionalSeat = allLockedSeats.stream()
                        .filter(s -> s.getSeatNumber().equals(seatDTO.getSeatNumber()))
                        .findFirst();

                if (optionalSeat.isPresent()) {
                    Seat seat = optionalSeat.get();

                    if (!seat.isAvailable() && seat.getBooking() != null && !seat.getBooking().getBookingId().equals(booking.getBookingId())) {
                        throw new seatNotAvailableException("Seat " + seatDTO.getSeatNumber() + " is already booked.");
                    }

                    seat.setSeatNumber(seatDTO.getSeatNumber());
                    seat.setPrice(seatDTO.getPrice());
                    seat.setIsAvailable(false);
                    seat.setBooking(booking);
                    seat.setShowtime(showtime);

                    booking.getSeats().add(seat);
                }

                else {
                    // Seat with this number doesn't exist in the database
                    throw new ResourceNotFoundException("Seat " + seatDTO.getSeatNumber() + " not found for showtime: " + showtime.getShowtimeId());
                }
            }
        }

        // Step 2: Identify seats to remove
        List<Seat> seatsToRemove = booking.getSeats().stream()
                .filter(seat -> !incomingSeatNumbers.contains(seat.getSeatNumber()))
                .toList();

        // Get seat numbers to remove
        List<String> seatNumbersToRemove = seatsToRemove.stream()
                .map(Seat::getSeatNumber)
                .toList();

        // Lock the seats that will be removed (pessimistic locking)
        if (!seatNumbersToRemove.isEmpty()) {
            List<Seat> lockedRemoveSeats = seatRepository.findAndLockSeatsByShowtimeAndSeatNumbers(
                    showtime.getShowtimeId(), seatNumbersToRemove);

            // Process seats being removed
            for (Seat seat : seatsToRemove) {
                seat.setIsAvailable(true);  // Mark the seat as available
                seat.setBooking(null);      // Detach the seat from the booking
                seatRepository.save(seat); // Save the updated seat
            }

            // Remove these seats from the booking's seat collection
            booking.getSeats().removeAll(seatsToRemove);
        }

        if (booking.getSeats().isEmpty()) {
            throw new seatNotAvailableException("Cannot save booking. The requested seats may be unavailable.");
        }

        // Recalculate totals
        booking.setNumOfTickets(booking.getSeats().size());
        booking.setTotalPrice(booking.getSeats().stream().mapToDouble(Seat::getPrice).sum());

        // Save the updated booking
        Booking savedBooking = bookingRepository.save(booking);

        return mapToResponseDTO(bookingRepository.save(booking));
    }

    // Delete booking
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.BOOKING_NOT_FOUND + id));

        // Mark seats as available and detach them from the booking
        for (Seat seat : booking.getSeats()) {
            seat.setIsAvailable(true);  // Mark seat as available
            seat.setBooking(null);      // Detach the seat from the booking
            seatRepository.save(seat); // Save the updated seat
        }

        bookingRepository.delete(booking);
    }

    private BookingResponseDTO mapToResponseDTO(Booking booking) {
        List<SeatDTO> seatDTOs = booking.getSeats().stream()
                .map(seat -> new SeatDTO(seat.getSeatId(), seat.getSeatNumber(), seat.getPrice(), seat.getSeatType()))
                .collect(Collectors.toList());

        return new BookingResponseDTO(
                booking.getBookingId(),
                booking.getShowtime().getShowtimeId(),
                booking.getUserName(),
                booking.getNumOfTickets(),
                booking.getTotalPrice(),
                seatDTOs,
                booking.getPurchaseTime()
        );
    }
}

