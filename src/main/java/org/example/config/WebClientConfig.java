package org.example.config;

import io.netty.handler.ssl.SslContextBuilder;
import org.apache.hc.client5.http.ContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.InputStream;

@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() throws SSLException {
    try {
      ClassPathResource trustStoreResource = new ClassPathResource("truststore.jks");
      InputStream trustStoreStream = trustStoreResource.getInputStream();

      SslContextBuilder sslContextBuilder = SslContextBuilder.forClient()
              .trustManager(trustStoreStream);

      HttpClient httpClient = HttpClient.create()
              .secure(ssl -> ssl.sslContext(sslContextBuilder));

      return WebClient.builder()
              .clientConnector(new ReactorClientHttpConnector(httpClient))
              .build();

    } catch (Exception e) {
      throw new SSLException("Failed to load truststore: " + e.getMessage());
    }
  }
}