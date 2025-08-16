package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReactiveExternalApiService {

  private final WebClient webClient;

  ReactiveExternalApiService(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<String> callExternalApi(String url) {
    return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class);
  }
}
