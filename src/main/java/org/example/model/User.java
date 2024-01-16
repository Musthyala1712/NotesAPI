package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @Field("email")
    @Indexed(name = "Email Index", unique = true)
    @Email
    private String email;

    @Field("password")
    private String password;

    @Field("firstName")
    @Size(min = 3, max = 16, message = "firstName must be of 3 - 16  characters")
    private String firstName;

    @Field("lastName")
    @Size(min = 3, max = 16, message = "lastName must be of 3 - 16  characters")
    private String lastName;

    @Size(min = 0, max = 10, message = "mobileNumber must be of 0 - 10  characters")
    @Field(value = "mobileNumber")
    @Indexed(name = "Mobile Number Index", unique = true)
    private String mobileNumber;
}
