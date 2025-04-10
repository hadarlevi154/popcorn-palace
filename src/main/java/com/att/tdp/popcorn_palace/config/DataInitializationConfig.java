package com.att.tdp.popcorn_palace.config;


import com.att.tdp.popcorn_palace.entity.*;
import com.att.tdp.popcorn_palace.enums.SeatType;
import com.att.tdp.popcorn_palace.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializationConfig {

    @Bean
    public CommandLineRunner initializeData(
            UserRepository userRepository,
            MovieRepository movieRepository,
            TheaterRepository theaterRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Only initialize if no data exists
            if (userRepository.count() == 0) {
                // Create Users
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@popcornpalace.com");
                adminUser.setPassword(passwordEncoder.encode("AdminPass123!"));

                User regularUser = new User();
                regularUser.setUsername("johndoe");
                regularUser.setEmail("johndoe@example.com");
                regularUser.setPassword(passwordEncoder.encode("UserPass123!"));

                userRepository.saveAll(Arrays.asList(adminUser, regularUser));

                // Create Theaters
                Theater mainTheater = new Theater();
                mainTheater.setTheaterName("Main Cinema");
                mainTheater.setTheaterLocation("Downtown");
                mainTheater.setTheaterCapacity(150);

                Theater premiumTheater = new Theater();
                premiumTheater.setTheaterName("Luxury Cinema");
                premiumTheater.setTheaterLocation("Uptown");
                premiumTheater.setTheaterCapacity(100);

                theaterRepository.saveAll(Arrays.asList(mainTheater, premiumTheater));

                // Create Movies
                Movie movie1 = new Movie();
                movie1.setTitle("Inception");
                movie1.setGenre("Sci-Fi");
                movie1.setReleaseYear(2010);
                movie1.setDuration(148);
                movie1.setRating(8.8);

                Movie movie2 = new Movie();
                movie2.setTitle("The Avengers");
                movie2.setGenre("Action");
                movie2.setReleaseYear(2012);
                movie2.setDuration(143);
                movie2.setRating(8.0);

                movieRepository.saveAll(Arrays.asList(movie1, movie2));

                // Create Showtimes
                Showtime showtime1 = new Showtime();
                showtime1.setMovie(movie1);
                showtime1.setTheater(mainTheater);
                showtime1.setStartTime(LocalDateTime.now().plusDays(1).withHour(18).withMinute(0));
                showtime1.setEndTime(LocalDateTime.now().plusDays(1).withHour(20).withMinute(28));
                showtime1.setPrice(12.50);

                Showtime showtime2 = new Showtime();
                showtime2.setMovie(movie2);
                showtime2.setTheater(premiumTheater);
                showtime2.setStartTime(LocalDateTime.now().plusDays(1).withHour(20).withMinute(0));
                showtime2.setEndTime(LocalDateTime.now().plusDays(1).withHour(22).withMinute(23));
                showtime2.setPrice(15.00);

                showtimeRepository.saveAll(Arrays.asList(showtime1, showtime2));

                // Create Seats
                List<Seat> seats1 = createSeatsForShowtime(showtime1);
                List<Seat> seats2 = createSeatsForShowtime(showtime2);

                seatRepository.saveAll(seats1);
                seatRepository.saveAll(seats2);
            }
        };
    }

    private List<Seat> createSeatsForShowtime(Showtime showtime) {
        return Arrays.asList(
                createSeat(showtime, "A1", SeatType.REGULAR, 12.50),
                createSeat(showtime, "A2", SeatType.REGULAR, 12.50),
                createSeat(showtime, "B1", SeatType.VIP, 15.00),
                createSeat(showtime, "B2", SeatType.VIP, 15.00),
                createSeat(showtime, "C1", SeatType.GOLDEN_RING, 20.00),
                createSeat(showtime, "C2", SeatType.GOLDEN_RING, 20.00),
                createSeat(showtime, "D1", SeatType.ACCESSIBLE, 12.50),
                createSeat(showtime, "D2", SeatType.ACCESSIBLE, 12.50)
        );
    }

    private Seat createSeat(Showtime showtime, String seatNumber, SeatType seatType, double price) {
        Seat seat = new Seat();
        seat.setShowtime(showtime);
        seat.setSeatNumber(seatNumber);
        seat.setSeatType(seatType);
        seat.setPrice(price);
        seat.setIsAvailable(true);
        return seat;
    }
}
