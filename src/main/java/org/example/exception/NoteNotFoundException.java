package org.example.exception;

public class NoteNotFoundException extends Exception {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
