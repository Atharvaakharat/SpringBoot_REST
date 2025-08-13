package org.example.controller;

import org.example.entity.ApiTracking;
import org.example.service.ApiTrackingService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/api/external")
public class ApiCallController {

  private final RestTemplate restTemplate;
  private final ApiTrackingService trackingService;

  public ApiCallController(RestTemplate restTemplate, ApiTrackingService trackingService) {
    this.restTemplate = restTemplate;
    this.trackingService = trackingService;
  }

  @GetMapping("/call")
  public ResponseEntity<String> callExternalApi() {
    String requestId = UUID.randomUUID().toString();
    String url = "https://restful-api.dev/api/mock"; // Replace with real URL

    Instant start = Instant.now();

    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    Instant end = Instant.now();
    long timeTaken = Duration.between(start, end).toMillis();

    ApiTracking tracking = new ApiTracking();
    tracking.setRequestId(requestId);
    tracking.setUrl(url);
    tracking.setHttpMethod("GET");
    tracking.setHttpStatus(response.getStatusCodeValue());
    tracking.setResponseBody(response.getBody());
    tracking.setTimeTakenMs(timeTaken);

    trackingService.saveTracking(tracking);

    return ResponseEntity.ok("API called. Request ID: " + requestId);
  }
}
