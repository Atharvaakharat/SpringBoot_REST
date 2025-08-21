package org.example.controller;

import org.example.service.ReactiveExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/api/reactive")
public class ReactiveApiController {

  private static final Logger logger = LoggerFactory.getLogger(ReactiveApiController.class);

  @Autowired
  private ReactiveExternalApiService reactiveService;

  @GetMapping("/call")
  public Mono<String> callReactiveApi() {
    String url = "https://jsonplaceholder.typicode.com/posts/1";
    logger.info("Calling external API: {}", url);
    return reactiveService.callExternalApi(url)
            .doOnSuccess(response -> logger.info("Received response: {}", response))
            .doOnError(error -> logger.error("Error during external API call", error));
  }
}
