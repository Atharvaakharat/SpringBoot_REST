package org.example.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Global exception handler test.
 */
public class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  /**
   * Test handle response status exception.
   */
  @Test
  public void testHandleResponseStatusException() {
    ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    ResponseEntity<String> response = handler.handleResponseStatusException(ex);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Not Found", response.getBody());
  }

  /**
   * Test handle general exception.
   */
  @Test
  public void testHandleGeneralException() {
    Exception ex = new Exception("Unexpected");
    ResponseEntity<String> response = handler.handleGeneralException(ex);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("An unexpected error occurred", response.getBody());
  }
}
