package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Service
public class ReactiveExternalApiService {

  @Autowired
  private WebClient webClient;

  public Mono<String> callExternalApi(String url) {
    return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String.class);
  }
}

