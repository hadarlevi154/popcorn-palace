package com.att.tdp.popcorn_palace.entity;


import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Database.USER_TABLE)
@Data // Provides getters, setters, equals, hashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Booking> bookings;

    @Override
    public String toString() {
        return "User(userId=" + userId +
                ", username='" + username + "'" +
                ", email='" + email + "'" +
                ", bookingsCount=" + (bookings != null ? bookings.size() : 0) +
                ")";
    }

    public Long getId() {
        return userId;
    }
}

