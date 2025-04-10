package com.att.tdp.popcorn_palace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.att.tdp.popcorn_palace.util.AppConstants.*;

@Entity
@Table(name = Database.THEATER_TABLE)
@Data // Provides getters, setters, equals, hashCode, etc.
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theaterid")
    private Long theaterId;

    @Column(name = "theater_name")
    private String theaterName;

    @Column(name = "theater_location")
    private String theaterLocation;

    @Column(name = "theater_capacity")
    private Integer theaterCapacity;

    @Override
    public String toString() {
        return "Theater(theaterId=" + theaterId +
                ", theaterName='" + theaterName + "'" +
                ", theaterLocation='" + theaterLocation + "'" +
                ", theaterCapacity=" + theaterCapacity +
                ")";
    }

    public String getName() {
        return theaterName;
    }
}