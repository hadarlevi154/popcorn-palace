# Popcorn-Palace
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
1) Create a Booking -

POST /api/bookings

Response status-

201 Created

Example of response-

{
"bookingId": 43,

"showtimeId": 1,

"userName": "hadarlevi154",

"numOfTickets": 1,

"totalPrice": 160.0,

"seats": [

{
"seatId": 18,

"seatNumber": "A2",

"seatType": "VIP",

"price": 160.0
}
],

"purchaseTime": "2025-04-10T22:14:34.1400671"

}

Error status-

409 Conflict

2) Update an Existing Booking (Can be partially) -

PUT /api/bookings/{bookingId}

Response status-

200 OK

Example of response-

{
"bookingId": 42,

"showtimeId": 1,

"userName": "hadarlevi154",

"numOfTickets": 1,

"totalPrice": 160.0,

"seats": [

{
"seatId": 18,

"seatNumber": "A2",

"seatType": "VIP",

"price": 160.0
}

],

"purchaseTime": "2025-04-10T16:16:11.749211"

}

Error status-

404 Not found
409 Conflict

3) Get Booking by Id -

GET /api/bookings/{bookingId}

Response status -

200

Example of response -

{
"bookingId": 1,
"showtimeId": 1,
"userName": "hadarlevi154",
"numOfTickets": 1,
"totalPrice": 100.0,
"seats": [
{
"seatId": 19,
"seatNumber": "A3",
"seatType": "VIP",
"price": 100.0
}
],
"purchaseTime": "2025-04-08T10:30:00"
}

Error status-

404 Not Found

4)Fetch all bookings -

GET /api/bookings

Response status -

200 OK

Example of Response- 
[
{
"bookingId": 1,
"showtimeId": 1,
"userName": "hadarlevi154",
"numOfTickets": 1,
"totalPrice": 150.0,
"seats": [
{
"seatId": 19,
"seatNumber": "A3",
"seatType": "VIP",
"price": 150.0
}
],
"purchaseTime": "2025-04-10T16:16:11.749211"
},
{
"bookingId": 2,
"showtimeId": 1,
"userName": "john_doe",
"numOfTickets": 1,
"totalPrice": 160.0,
"seats": [
{
"seatId": 18,
"seatNumber": "A2",
"seatType": "VIP",
"price": 160.0
}
],
"purchaseTime": "2025-04-09T21:10:02.230374"
}
]

Error status-

500 Internal Server Error

5) Delete a booking -

DELETE /api/bookings/{bookingId}

Response status -

204 No Content

Error status-

404 Not Found


Movies:
1) Create a Movie -
   POST /api/movies

Response status-

201 Created

Example of response-

{
"movieId": 7,
"title": "AquaMan 4",
"genre": "Scify",
"duration": 130,
"rating": 9.0,
"releaseYear": 2025
}

Error status-

400 Bad Request

2) Update an Existing movie (Can be partially) -
   PUT /api/movies/{movieId}

Status code-

200 OK

Example of response-
{
"movieId": 1,
"title": "AquaMan 2",
"genre": "Scify",
"duration": 148,
"rating": 9.2,
"releaseYear": 2018
}

Error status-

404 Not found

3) Delete a movie -
   DELETE /api/movies/{movieId}

Response status-

204 No content

Error status-

404 Not found

4) Fetch all movies -
   GET /api/movies

Response status-

200 OK

Example of response-

[
{
"movieId": 1,
"title": "Inception",
"genre": "Sci-Fi",
"duration": 148,
"rating": 8.8,
"releaseYear": 2010
},
{
"movieId": 2,
"title": "The Avengers",
"genre": "Action",
"duration": 143,
"rating": 8.0,
"releaseYear": 2012
}
]

Error status-

500 Internal Server Error

5) Fetch movie by Id -
   GET /api/movies/{movieId}

Response status-

200 OK

Example of response-

{
"movieId": 1,
"title": "Inception",
"genre": "Sci-Fi",
"duration": 148,
"rating": 8.8,
"releaseYear": 2010
}

Error status-

404 Not Found

Showtimes:
1) Add showtime -
   POST /api/showtimes

Response status-

201 Created

Example of Response-

{
"showtimeId": 6,
"movieId": 1,
"theaterId": 1,
"startTime": "2026-04-10T21:30:02.257482",
"endTime": "2026-04-10T23:23:02.257482",
"price": 15.0
}

Error status-

404 Not Found
400 Bad Request

2) Update showtime details (Can be partially) -
   PUT /api/showtimes/{showtimeId}

Response status-

200 OK

Example of Response-

{
    "showtimeId": 1,
    "movieId": 1,
    "theaterId": 1,
    "startTime": "2025-04-11T18:00:02.256907",
    "endTime": "2026-04-11T21:31:02.257482",
    "price": 12.5
}

Error status-

404 Not Found

3) Delete a showtime -
   DELETE /api/showtimes/{showtimeId}

Response status-

204 No Content

Error status-

404 Not Found

4) Fetch showtime by id -
   GET /api/showtimes/{showtimeId}

Response status-

200 OK

Example of response-
{
"showtimeId": 1,
"movieId": 1,
"theaterId": 1,
"startTime": "2025-04-11T18:00:02.256907",
"endTime": "2026-04-11T21:31:02.257482",
"price": 12.5
}

Error status-

404 Not Found


5) Fetch all showtimes -
   GET /api/showtimes

Response status-

200 OK

Example of response-

[
{
"showtimeId": 6,
"movieId": 1,
"theaterId": 1,
"startTime": "2026-04-10T21:30:02.257482",
"endTime": "2026-04-10T23:23:02.257482",
"price": 15.0
},
{
"showtimeId": 1,
"movieId": 1,
"theaterId": 1,
"startTime": "2025-04-11T18:00:02.256907",
"endTime": "2026-04-11T21:31:02.257482",
"price": 12.5
}
]

Error status -
500 Internal Server Error

Users:
1) Register a new user -
   POST /api/users

Response status-

201 Created

Example of response-

{
"username": "hadarlevi154",
"email": "hadarlevi154@gmail.com"
}

Error status -

400 Bad request

2) Fetch user by id -
   GET /api/users/{userId}

Response status-

200 OK

Example of response-

{
"username": "admin",
"email": "admin@popcornpalace.com"
}

Error status -

404 Not Found

3) Fetch user by username -
   GET /api/users/{userName}

Response status-

200 OK

Example of response-

{
"username": "hadarlevi154",
"email": "hadarlevi154@gmail.com"
}


Error status -

404 Not found


4) Fetch all users -
   GET /api/users

Response status-

200 OK

Example of response-

[
{
"username": "admin",
"email": "admin@popcornpalace.com"
},
{
"username": "johndoe",
"email": "johndoe@example.com"
},
{
"username": "hadarlevi154",
"email": "hadarlevi154@gmail.com"
}
]

Error status -

500 Internal Server Error


Theaters:
1) Create a new theater -
   POST /api/theaters

Response status-

201 Created

Example of response-

{
"theaterId": 1,
"theaterName": "VIP Cinema",
"theaterLocation": "Tel Aviv",
"theaterCapacity": 1000
}

Error status -

400 Bad Request

2) Fetch theater by id -
   GET /api/theaters/{theaterId}

Response status-

200 OK

Example of response-

{
"theaterId": 1,
"theaterName": "Main Cinema",
"theaterLocation": "Downtown",
"theaterCapacity": 150
}

Error status-

404 Not Found

3) Fetch all theaters -
   GET /api/theaters

Response status-

200 OK

Example of response-

[
{
"theaterId": 1,
"theaterName": "Main Cinema",
"theaterLocation": "Downtown",
"theaterCapacity": 150
},
{
"theaterId": 2,
"theaterName": "Luxury Cinema",
"theaterLocation": "Uptown",
"theaterCapacity": 100
}
]

Error status -

500 Internal Server Error

4) Update theater Inforamtion (such as capacity etc.) -
   PUT /api/theaters/{theaterId}

Response status-

200 OK

Example of response-

{
"theaterId": 6,
"theaterName": "Luxury Cinema Updated Capacity",
"theaterLocation": "Uptown",
"theaterCapacity": 150
}

Error status -

404 Not Found


5) Delete a theater -
   DELETE /api/theaters/{theaterId}

Response status-

204 No content

Error status-

404 Not found

Seats:
1) get available seats by show -
   GET /showtimes/{showtimeid}/seats

Response status-

200 OK

Example of response-

[
{
"seatId": 19,
"seatNumber": "A3",
"seatType": "VIP",
"price": 150.0
}
]

2) get seats by booking-
   GET /bookings/{bookingId}/seats

Response status-

200 OK

Example of response-

[
{
"seatId": 19,
"seatNumber": "A3",
"seatType": "VIP",
"price": 150.0
}
]


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
