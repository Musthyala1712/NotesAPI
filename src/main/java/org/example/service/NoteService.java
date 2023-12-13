package org.example.service;

import org.example.response.ResponseBase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NoteService {
    public ResponseBase info() {
        ResponseBase responseBase = new ResponseBase();
        responseBase.valid = true;
        responseBase.completed = LocalDateTime.now();
        return responseBase;
    }
}
