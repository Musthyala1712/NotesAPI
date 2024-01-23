package org.example.exception;

import org.example.response.ResponseBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseBase<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error: errors) {
            errorMessages.add(error.getDefaultMessage());
        }
//        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream()
//                .map(oe -> oe.getDefaultMessage()).collect(Collectors.toList());
        ResponseBase<Void> response = new ResponseBase<>(false, errorMessages, null);
        return ResponseEntity.badRequest().body(response);
    }



    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseBase<Void>> handleNoteNotFoundException(NoteNotFoundException exception, WebRequest webRequest) {
        ResponseBase<Void> response = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseBase<Void>> handleException(Exception exception, WebRequest webRequest) {
        ResponseBase<Void> response = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseBase<Void>> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest webRequest) {
        ResponseBase<Void> responseBase = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.badRequest().body(responseBase);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseBase<Void>> handleUserNotFoundException(UserNotFoundException exception, WebRequest webRequest) {
        ResponseBase<Void> responseBase = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.badRequest().body(responseBase);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseBase<Void>> handleUserNameNotFoundException(UsernameNotFoundException exception, WebRequest webRequest) {
        ResponseBase<Void> responseBase = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.badRequest().body(responseBase);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ResponseBase<Void>> handleAuthenticationException(AuthenticationException exception, WebRequest webRequest) {
        ResponseBase<Void> responseBase = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.badRequest().body(responseBase);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseBase<Void>> handleJwtTokenExpiredException(JwtTokenExpiredException exception, WebRequest webRequest) {
        ResponseBase<Void> responseBase = new ResponseBase<>(false, List.of(exception.getMessage()), null);
        return ResponseEntity.internalServerError().body(responseBase);
    }
}
