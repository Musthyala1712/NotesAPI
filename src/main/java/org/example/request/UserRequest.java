package org.example.request;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UserRequest {
    @Email(message = "Email must be a well-formed email address")
    @NotEmpty
    private String email;

    @NotEmpty(message = "Password cannot be empty or null")
    private String password;

    @NotEmpty(message = "First name cannot be empty or null")
    @Size(min = 3, max = 16, message = "firstName must be of 3 - 16  characters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty or null")
    @Size(min = 3, max = 16, message = "lastName must be of 3 - 16  characters")
    private String lastName;

    @Size(min = 0, max = 10, message = "mobileNumber must be of 0 - 10  characters")
    private String mobileNumber;
}
