package com.att.tdp.popcorn_palace.dto.userdto;


import com.att.tdp.popcorn_palace.util.AppConstants.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data // Provides getters, setters, equals, hashCode, and toString methods
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO implements Serializable {

    @NotBlank(message = ValidationMessages.NAME_REQUIRED)
    @Size(min = validationVariables.MIN_USERNAME_LENGTH, max = validationVariables.MAX_USERNAME_LENGTH)
    private String username;

    @NotBlank(message = ValidationMessages.EMAIL_REQUIRED)
    @Email(message = ValidationMessages.VALID_EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = ValidationMessages.PASSWORD_REQUIRED)
    @Size(min = validationVariables.MIN_PASSWORD_LENGTH, max = validationVariables.MAX_PASSWORD_LENGTH)
    private String password;
}

