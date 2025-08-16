package org.example.controller;

import org.example.service.ReactiveExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reactive")
public class ReactiveApiController {

  private final ReactiveExternalApiService reactiveService;

  public ReactiveApiController(ReactiveExternalApiService reactiveService) {
    this.reactiveService = reactiveService;
  }

  @GetMapping("/call")
  public Mono<String> callReactiveApi() {
    String url = "https://restful-api.dev/api/mock"; // Replace with actual endpoint
    return reactiveService.callExternalApi(url);
  }
}
