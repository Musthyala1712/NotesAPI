package org.example.controller;

import org.example.response.ResponseBase;
import org.example.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
// controller - service (business logic) - repository
public class NotesController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/info")
    public ResponseEntity<ResponseBase> info() {
        ResponseBase responseBase = noteService.info();
        return ResponseEntity.ok(responseBase);
    }

    @GetMapping("/hello")
    public ResponseEntity<ResponseBase> hello() {
        ResponseBase responseBase = noteService.info();
        return ResponseEntity.ok(responseBase);
    }

    @PostMapping
    public ResponseEntity<ResponseBase> information() {
        ResponseBase responseBase = noteService.info();
        return ResponseEntity.ok(responseBase);
    }

}
