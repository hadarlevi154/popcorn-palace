package com.att.tdp.popcorn_palace.dto.userdto;


import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO implements Serializable {

    private String username;
    private String email;
}

