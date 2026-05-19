package alansilva.task_manager.exceptions;

import alansilva.task_manager.dtos.response.MessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageResponseDTO> handleException(
            RuntimeException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDTO(
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponseDTO> handleException(
            MethodArgumentNotValidException ex
    ) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponseDTO(message));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<MessageResponseDTO> handleException(
            AuthorizationDeniedException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponseDTO(
                        "Acesso negado!"
                ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<MessageResponseDTO> handleInvalidCredentials(
            InvalidCredentialsException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponseDTO(
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponseDTO> handleUserNotFound(
            UserNotFoundException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResponseDTO(
                        ex.getMessage()
                ));
    }
}

