package org.example.repository;

import org.example.entity.ApiTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiTrackingRepository extends JpaRepository<ApiTracking, Long> {
}