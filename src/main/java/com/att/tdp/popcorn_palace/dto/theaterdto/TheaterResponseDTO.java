package com.att.tdp.popcorn_palace.dto.theaterdto;

import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class TheaterResponseDTO implements TheaterDTOBase, Serializable {
    private Long theaterId;
    private String theaterName;
    private String theaterLocation;
    private Integer theaterCapacity;
}
