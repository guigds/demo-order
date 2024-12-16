package com.order.config;

import com.order.dto.ErrorResponseDto;
import com.order.dto.ValidationErrorResponseDto;
import com.order.exception.ProdutoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String SOLICITACAO_NAO_CONCLUIDA = "A solicitação não pôde ser concluída.";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ValidationErrorResponseDto response = ValidationErrorResponseDto.builder()
                .message(SOLICITACAO_NAO_CONCLUIDA)
                .errors(errors).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(ProdutoNotFoundException ex) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .message(SOLICITACAO_NAO_CONCLUIDA)
                .detail(ex.getMessage() + ". Por favor, verifique se os dados estão corretos e tente novamente.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}