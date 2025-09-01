package org.example.config;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() throws Exception {
    ClassPathResource trustStoreResource = new ClassPathResource("truststore.jks");
    char[] trustStorePassword = "changeit".toCharArray();

    SSLContext sslContext = SSLContextBuilder.create()
            .loadTrustMaterial(trustStoreResource.getFile(), trustStorePassword, new TrustSelfSignedStrategy())
            .build();

    CloseableHttpClient httpClient = HttpClients.custom()
            .setUserAgent(String.valueOf(sslContext))
            .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(Timeout.ofMilliseconds(5000).toDuration());
    factory.setConnectTimeout(Timeout.ofMilliseconds(5000).toDuration());

    return new RestTemplate(factory);
  }
}
