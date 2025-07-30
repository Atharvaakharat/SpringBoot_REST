package org.example.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

  private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

  /**
   * Handle response status exception response entity.
   *
   * @param ex the exception
   * @return the response entity
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
    logger.warn("Handled ResponseStatusException: {} - {}", ex.getStatusCode(), ex.getReason(), ex);
    return new ResponseEntity<>(ex.getReason(), ex.getStatusCode());
  }

  /**
   * Handle general exception response entity.
   *
   * @param ex the exception
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    logger.error("Unhandled exception occurred", ex);
    return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
