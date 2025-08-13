package org.example.controller;

import org.example.service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/external")
public class ExternalApiController {

  private final ExternalApiService externalApiService;

  public ExternalApiController(ExternalApiService externalApiService) {
    this.externalApiService = externalApiService;
  }

  @GetMapping("/test")
  public String testApi() {
    return externalApiService.callExternalApi("https://restful-api.dev/your-endpoint");
  }
}