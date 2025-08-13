package org.example.service;

import org.example.entity.ApiTracking;
import org.example.repository.ApiTrackingRepository;
import org.springframework.stereotype.Service;

@Service
public class ApiTrackingService {

  private final ApiTrackingRepository repository;

  public ApiTrackingService(ApiTrackingRepository repository) {
    this.repository = repository;
  }

  public void saveTracking(ApiTracking tracking) {
    repository.save(tracking);
  }
}