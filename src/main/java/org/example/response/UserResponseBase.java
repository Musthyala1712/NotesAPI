package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseBase<T> {
    private boolean valid;
    private List<String> messages;
    private T data;
}
