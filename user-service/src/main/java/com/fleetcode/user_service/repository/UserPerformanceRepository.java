package com.fleetcode.user_service.repository;

import com.fleetcode.user_service.model.User;
import com.fleetcode.user_service.model.UserPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, Integer> {
    Optional<UserPerformance> findByUserId(Long userId);
}
