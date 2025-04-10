package com.att.tdp.popcorn_palace.util;


public class AppConstants {

    private AppConstants() {
        throw new AssertionError("Constants class should not be instantiated");
    }

    public static final class validationVariables {

        public static final int MAX_TITLE_LENGTH = 255;
        public static final int MIN_TITLE_LENGTH = 1;

        public static final int MAX_GENRE_LENGTH = 100;
        public static final int MIN_GENRE_LENGTH = 1;

        public static final int MAX_NAME_LENGTH = 255;
        public static final int MIN_NAME_LENGTH = 1;

        public static final int MIN_SEAT_NUMBER = 1;

        public static final int MIN_RATING = 0;
        public static final int MAX_RATING = 10;

        public static final int MIN_RELEASE_YEAR = 1900;
        public static final int MAX_RELEASE_YEAR = 2100;

        public static final boolean DEFAULT_IS_SEAT_AVAILABLE = true;

        public static final int MIN_USERNAME_LENGTH = 1;
        public static final int MAX_USERNAME_LENGTH = 20;

        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final int MAX_PASSWORD_LENGTH = 64;

    }

    public static final class ValidationMessages {
        public static final String TITLE_REQUIRED = "Title is required";
        public static final String GENRE_REQUIRED = "Genre is required";
        public static final String DURATION_REQUIRED = "Duration is required";
        public static final String RATING_REQUIRED = "Rating is required";
        public static final String RELEASE_YEAR_REQUIRED = "Release year is required";
        public static final String NAME_REQUIRED = "Name is required";
        public static final String SEAT_NUMBER_REQUIRED = "Seat number is required";
        public static final String THEATER_LOCATION_REQUIRED = "Theater location is required";
        public static final String SHOWTIME_REQUIRED = "Showtime is required";
        public static final String START_TIME_REQUIRED = "Start time is required";
        public static final String END_TIME_REQUIRED = "End time is required";
        public static final String PRICE_REQUIRED = "Price is required";
        public static final String NUMBER_OF_TICKETS_REQUIRED = "Number of tickets is required";
        public static final String TOTAL_PRICE_REQUIRED = "Total price is required";
        public static final String PURCHASE_TIME_REQUIRED = "Purchase time is required";
        public static final String USER_ID_REQUIRED = "User Id is required";
        public static final String USER_NAME_REQUIRED = "User name is required";
        public static final String SEAT_ID_REQUIRED = "Seat Id is required";
        public static final String SEAT_TYPE_REQUIRED = "Seat Type is required";
        public static final String IS_AVAILABLE_REQUIRED = "Is available parameter is required";
        public static final String THEATER_ID_REQUIRED = "Theater Id is required";
        public static final String MOVIE_ID_REQUIRED = "Movie Id is required";
        public static final String THEATER_CAPACITY_REQUIRED = "Theater Capacity is required";
        public static final String EMAIL_REQUIRED = "Email is required";
        public static final String VALID_EMAIL_REQUIRED = "Valid Email address is required";
        public static final String PASSWORD_REQUIRED = "Password is required";

        public static final String DURATION_POSITIVE = "Duration must be positive";
        public static final String CAPACITY_POSITIVE = "Capacity must be positive";
        public static final String PRICE_POSITIVE = "Price must be positive";
        public static final String NUMBER_OF_TICKETS_POSITIVE = "Number of tickets must be positive";
        public static final String TOTAL_PRICE_POSITIVE = "Total price must be positive";
        public static final String THEATER_CAPACITY_POSITIVE = "Theater capacity must be positive";

        public static final String TIME_FUTURE_OR_PRESENT = "Time should be future or present";
        public static final String TIME_FUTURE = "Time should be future";

    }

    public static class ErrorMessages {
        public static final String MOVIE_NOT_FOUND = "Movie not found with ID: ";
        public static final String USER_NOT_FOUND = "User not found with ID: ";
        public static final String THEATER_NOT_FOUND = "Theater not found with ID: ";
        public static final String SHOWTIME_NOT_FOUND = "Showtime not found with ID: ";
        public static final String USERNAME_NOT_FOUND = "Username not found: ";
        public static final String BOOKING_NOT_FOUND = "Booking not found with ID: ";

        public static final String USERNAME_EXISTS = "Username already exists";
        public static final String EMAIL_EXISTS = "Email already exists";
    }

    public static class Database {
        public static final String MOVIE_TABLE = "Movies";
        public static final String THEATER_TABLE = "Theaters";
        public static final String SHOWTIME_TABLE = "Showtimes";
        public static final String BOOKING_TABLE = "Bookings";
        public static final String SEAT_TABLE = "Seats";
        public static final String USER_TABLE = "Users";
    }

    public static class SQLQueries {
        public static final String SQL_FIND_OVERLAP_SHOWTIME =
                "SELECT s FROM Showtime s " +
                "WHERE s.theater.id = :theaterId " +
                "AND (s.startTime <= :endTime AND s.endTime >= :startTime)" +
                "AND (:excludeId IS NULL OR s.showtimeId <> :excludeId)";

        public static final String SQL_FIND_AVAILABLE_SEATS = "SELECT s FROM Seat s WHERE s.seatId IN :seatIds AND s.isAvailable = true";
        public static final String SQL_FIND_SEATS_BY_SHOWTIME_SEATNUMBER = "SELECT s FROM Seat s WHERE s.showtime.showtimeId = :showtimeId AND s.seatNumber IN (:seatNumbers)";
        public static final String SQL_DELETE_SHOWTIME_WHEN_DELETING_MOVIE = "DELETE FROM Showtime s WHERE s.movie.movieId = :movieId";
    }

}
