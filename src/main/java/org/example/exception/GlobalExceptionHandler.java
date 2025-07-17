package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Global exception handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handle response status exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
    return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
  }

  /**
   * Handle general exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
