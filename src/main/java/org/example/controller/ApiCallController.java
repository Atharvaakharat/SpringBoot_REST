
package org.example.controller;

import org.example.entity.ApiTracking;
import org.example.service.ApiTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/external")
public class ApiCallController {

  @Autowired
  private RestTemplate restTemplate;
  private ApiTrackingService trackingService;

  public ApiCallController(RestTemplate restTemplate, ApiTrackingService trackingService) {
    this.restTemplate = restTemplate;
    this.trackingService = trackingService;
  }

  @GetMapping("/call")
  public ResponseEntity<String> callExternalApi() {
    return performApiCall("https://jsonplaceholder.typicode.com/posts/1", HttpMethod.GET, null);
  }

  @PostMapping("/call")
  public ResponseEntity<String> postExternalApi(@RequestBody String requestBody) {
    return performApiCall("https://jsonplaceholder.typicode.com/posts", HttpMethod.POST, requestBody);
  }

  @PutMapping("/call")
  public ResponseEntity<String> putExternalApi(@RequestBody String requestBody) {
    return performApiCall("https://jsonplaceholder.typicode.com/posts/1", HttpMethod.PUT, requestBody);
  }

  private ResponseEntity<String> performApiCall(String url, HttpMethod method, String requestBody) {
    String requestId = UUID.randomUUID().toString();
    Instant start = Instant.now();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

    Instant end = Instant.now();
    long timeTaken = Duration.between(start, end).toMillis();

    ApiTracking tracking = new ApiTracking();
    tracking.setRequestId(requestId);
    tracking.setUrl(url);
    tracking.setHttpMethod(method.name());
    tracking.setHttpStatus(response.getStatusCodeValue());
    tracking.setResponseBody(response.getBody());
    tracking.setTimeTakenMs(timeTaken);

    trackingService.saveTracking(tracking);

    return ResponseEntity.ok(method.name() + " API called. Request ID: " + requestId);
  }
}
