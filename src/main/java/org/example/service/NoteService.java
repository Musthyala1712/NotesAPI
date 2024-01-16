package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.NoteNotFoundException;
import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Note create(String title, String body) {
        log.info("Creating new note for user...");
        Note note = Note.builder().title(title).body(body).build();
        return noteRepository.save(note);
    }

    public Note update(String id, String title, String body) throws NoteNotFoundException {
        Optional<Note> foundNote = noteRepository.findById(id);
        if (foundNote.isPresent()) {
            log.info("updating note for the user with id -  " + id);
            Note note = foundNote.get();
            note.setTitle(title);
            note.setBody(body);
            return noteRepository.save(note);
        }
        throw new NoteNotFoundException("Note note found!");
    }

    public List<Note> findAllNotes() {
        log.info("find all Notes... ");
        return noteRepository.findAll();
    }

    public Note findOne(String id) throws NoteNotFoundException {
        log.info("found a Note...");
        Optional<Note> foundNote = noteRepository.findById(id);
        if (foundNote.isPresent()) {
            return foundNote.get();
        }
        throw new NoteNotFoundException("Note not found");
    }

    public void deleteOne(String id) throws NoteNotFoundException {
        log.info("deleting a note...");
        boolean exists = noteRepository.existsById(id);
        if(exists) {
            noteRepository.deleteById(id);
            return;
        }
        throw new NoteNotFoundException("Note not found");
    }
}
