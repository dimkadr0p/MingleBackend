package ru.khachidze.backend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.khachidze.backend.api.validation.ValidationRegex;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDto {

    @NotBlank(message = "The login must be filled in")
    @Pattern(regexp = ValidationRegex.LOGIN_REGEX,  message = "Invalid login format")
    private String name;

    @NotBlank(message = "The password must be filled in")
    @Pattern(regexp = ValidationRegex.PASSWORD_REGEX, message = "Password must contain at least one digit, " +
                                "one lowercase letter, one uppercase letter, and be at least 8 characters long")
    private String password;

    @NotBlank(message = "The email must be filled in")
    @Email(message = "Invalid email format")
    private String email;
}
