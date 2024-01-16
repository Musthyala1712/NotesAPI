package org.example.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequest {
    @NotEmpty(message = "Note title cannot be empty")
    @Size(min = 3, max = 128, message = "Note title should be of size 3 to 128 characters")
    private String title;

    @Size(min = 3, max = 256, message = "Note body should be of size 3 to 256 characters")
    private String body;
}
