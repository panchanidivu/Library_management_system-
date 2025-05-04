package com.example.librarymanagement.exceptions;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.librarymanagement.responseDTO.CustomResponseEntity;
import com.example.librarymanagement.responseDTO.CustomResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex) {
        CustomResponseEntity response = CustomResponseEntity.builder()
                .code(404)
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //  validation exceptions 
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleValidationExceptions(BindException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError) 
                .map(error -> ((FieldError) error).getDefaultMessage()) 
                .collect(Collectors.toList());

        String message = "Validation failed";
        Object errorData = errorMessages.isEmpty() ? null : errorMessages;

        CustomResponseEntity errorResponse = new CustomResponseEntity(
                HttpStatus.BAD_REQUEST.value(),
                0, 
                message,
                errorData);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CustomResponseEntity handleCustomException(CustomException ex, WebRequest request) {
        return CustomResponseEntity.builder()
                .code(ex.getHttpStatusCode())
                .status(CustomResponseStatus.FAILURE.getStatus())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        CustomResponseEntity response = CustomResponseEntity.builder()
                .code(500) 
                .message("An unexpected error occurred") 
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()) 
                .data(ex.getMessage()) 
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
