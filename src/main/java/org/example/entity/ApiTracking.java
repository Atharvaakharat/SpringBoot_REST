package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_tracking")
public class ApiTracking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String requestId;
  private String url;
  private String httpMethod;
  private int httpStatus;

  @Column(columnDefinition = "TEXT")
  private String responseBody;

  private long timeTakenMs;
  private LocalDateTime createdAt;

  public ApiTracking() {
    this.createdAt = LocalDateTime.now();
  }

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getRequestId() { return requestId; }
  public void setRequestId(String requestId) { this.requestId = requestId; }

  public String getUrl() { return url; }
  public void setUrl(String url) { this.url = url; }

  public String getHttpMethod() { return httpMethod; }
  public void setHttpMethod(String httpMethod) { this.httpMethod = httpMethod; }

  public int getHttpStatus() { return httpStatus; }
  public void setHttpStatus(int httpStatus) { this.httpStatus = httpStatus; }

  public String getResponseBody() { return responseBody; }
  public void setResponseBody(String responseBody) { this.responseBody = responseBody; }

  public long getTimeTakenMs() { return timeTakenMs; }
  public void setTimeTakenMs(long timeTakenMs) { this.timeTakenMs = timeTakenMs; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
