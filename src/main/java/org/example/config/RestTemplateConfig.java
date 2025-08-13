package org.example.config;

import org.apache.hc.client5.http.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.ClientTlsStrategyBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() throws Exception {
    File trustStoreFile = new File("C:/SSL/truststore.jks");

    SSLContext sslContext = SSLContexts.custom()
            .loadTrustMaterial(trustStoreFile, "changeit".toCharArray())
            .build();

    CloseableHttpClient httpClient = HttpClients.custom()
            .setTlsStrategy(ClientTlsStrategyBuilder.create()
                    .setSslContext(sslContext)
                    .build())
            .build();

    HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

    // These work with Spring Boot 3 + HttpClient 5
    requestFactory.setConnectTimeout((int) Timeout.ofSeconds(10).toMilliseconds());
    requestFactory.setReadTimeout((int) Timeout.ofSeconds(30).toMilliseconds());

    return new RestTemplate(requestFactory);
  }
}