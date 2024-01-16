package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.NoteNotFoundException;
import org.example.model.Note;
import org.example.request.NoteRequest;
import org.example.response.ResponseBase;
import org.example.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/note")
@Slf4j
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    @Operation(
            summary = "Creates a note in the database",
            description = "Creates a new note in database provided title and body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")}),
    })
    public ResponseEntity<ResponseBase<Note>> create(@RequestBody @Valid NoteRequest noteRequest) {
        Note createdNote = noteService.create(noteRequest.getTitle(), noteRequest.getBody());
        ResponseBase<Note> response = new ResponseBase<>(true, null, createdNote);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(
            summary = "Updates a Note provided a id along with request body",
            description = "Updates a note in database provided an id where the request body has title and body")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBase<Note>> update(@PathVariable("id") String id, @RequestBody @Valid NoteRequest noteRequest) throws NoteNotFoundException {
        Note updatedNote = noteService.update(id, noteRequest.getTitle(), noteRequest.getBody());
        ResponseBase<Note> response = new ResponseBase<>(true, null, updatedNote);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Finds all the notes",
            description = "Finds all the notes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")}),
    })
    @GetMapping("/all")
    public ResponseEntity<ResponseBase<List<Note>>> findAll() {
        List<Note> foundNotes = noteService.findAllNotes();
        ResponseBase<List<Note>> response = new ResponseBase<>(true, null, foundNotes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Finds a note with an id",
            description = "Finds a note with an id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBase<Note>> findOne(@PathVariable("id") String id) throws NoteNotFoundException {
        Note findOne = noteService.findOne(id);
        ResponseBase<Note> response = new ResponseBase<>(true, null, findOne);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletes a note",
            description = "Deletes a note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ResponseBase.class), mediaType = "application/json")})
    })
    public ResponseEntity<ResponseBase<Void>> deleteOne(@PathVariable("id") String id) throws NoteNotFoundException {
        noteService.deleteOne(id);
        ResponseBase<Void> response = new ResponseBase<>(true, null, null);
        return ResponseEntity.ok(response);
    }
}
