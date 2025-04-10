# popcorn-palace
Movie Ticket Booking System “Popcorn Palace”- Backend Development

## Description

Welcome to Popcorn Palace, a modern and scalable movie ticket booking system designed to provide a seamless user experience for movie lovers. This system allows users to view available showtimes, make bookings, select seats, and complete their purchase efficiently. It supports real-time seat availability checking, seat locking, and booking updates, making it perfect for handling busy movie theaters.

## Key Features

**Ticket Booking System**	
Key Features:
○	Allow users to book tickets for available showtimes (Create booking).

○	Fetch booking by id.

○	Fetch all bookings.

○	Update a booking to add or remove seats. The system automatically handles price recalculation and seat availability.

○	Delete a booking. The system automatically handles seat availability.

●	Constraints:

○	Ensure no seat is booked twice for the exact showtime, using locking.

**Movie Management:**
Key Features:
○	Add new movies with details: title, genre, duration, rating, release_year.
○	Update movie information (Can be partially). 
○	Delete a movie.
○	Fetch all movies.
○	Fetch movie by Id.

**Showtime Management**
Key Features:
○	Add showtimes for movies with details: movie, theater, start_time, end_time, price.
○	Update showtime details (Can be partially).
○	Delete a showtime.
○	Fetch showtime by id.
○	Fetch all showtimes.
●	Constraints:
○	No overlapping showtimes for the same theater.

**users Management**
Key Features:
○	Register a new user.
○	Fetch user by id.
○	Fetch user by username.
○	Fetch all users.
●	Constraints:
○	Can't choose the same user name more than once.

**Theaters Management**
Key Features:
○	Create a new theater.
○	Fetch theater by id.
○	Fetch all theaters.
○	Update theater Inforamtion (such as capacity etc.).
○	Delete a theater.

**Seats Management**
Key Features:
○	get available seats by show.
○	get seats by booking.


**Error Handling**
The system provides clear and user-friendly error messages in case of issues such as double-booking, unavailable seats, invalid data, etc.
For example:
- Secure user registration with username and email validation
- Password encryption using Spring Security
- Comprehensive error handling
- Validation to prevent invalid data (For example- Negative number of tickets).
- etc.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JPA/Hibernate
- Maven
- PostgreSQL
- REST API
- Error Handling
- Transactional Management

## Prerequisites

- Java 17 or higher
- Maven or Gradle
- Database (PostgreSQL recommended)

## Installation

### Clone the Repository

```bash
git clone https://github.com/yourusername/user-registration-service.git
cd user-registration-service
```

### Build the Project

Using Maven:
```bash
mvn clean install
```

Using Gradle:
```bash
gradle clean build
```

## Configuration

### Application Properties

Configure your `application.properties` or `application.yml`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/userdb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Security Configuration
# Add any specific security configurations
```


## API Endpoints

Bookings:
1) Create a Booking
POST /api/bookings

2) Update an Existing Booking (Can be partially).
PUT /api/bookings/{bookingId}

3) Get Booking by Id
   GET /api/bookings/{bookingId}
   
6) Fetch all bookings.
  GET /api/bookings

5) Delete a booking.
  DELETE /api/bookings/{bookingId}

Movies:
1) Create a Movie
   POST /api/movies
   
2) Update an Existing movie (Can be partially)
   PUT /api/movies/{movieId}
   
3) Delete a movie.
   DELETE /api/movies/{movieId}
   
4) Fetch all movies.
   GET /api/movies
   
5) Fetch movie by Id.
   GET /api/movies/{movieId}

Showtimes:
1) Add showtime for movie
2) Update showtime details (Can be partially).
3) Delete a showtime.
3) Fetch showtime by id.
4) Fetch all showtimes.

Users:
1) Register a new user.
2) Fetch user by id.
3) Fetch user by username.
4) Fetch all users.

Theaters:
1) Create a new theater.
2) Fetch theater by id.
3) Fetch all theaters.
4) Update theater Inforamtion (such as capacity etc.).
5) Delete a theater.

Seats:
1) get available seats by show.
2) get seats by booking.

## Security Considerations

- Passwords are encrypted using Spring Security's `PasswordEncoder`
- Comprehensive validation checks
- Custom exception handling to prevent information leakage

## Testing

### Running Tests

```bash
# Maven
mvn test

# Gradle
gradle test
```

## Potential Improvements

- Add email verification
- Implement password strength validation
- Create more granular error responses
- Add rate limiting for registration attempts

## Deployment

Ensure to:
- Set up proper database configurations
- Configure production-specific security settings
- Set up appropriate logging levels

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push and create a pull request

## Contact

Hadar Levi
hadarlevi154@gmail.com
