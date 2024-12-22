package com.fleetcode.user_service.service;

import com.fleetcode.user_service.model.User;
import com.fleetcode.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fleetcode.user_service.service.UserPerformanceService;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPerformanceService userPerformanceService;

    @Autowired
    public UserService(UserRepository userRepository, UserPerformanceService userPerformanceService) {
        this.userRepository = userRepository;
        this.userPerformanceService = userPerformanceService;
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean existsUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

    public Boolean existsUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }

    public User createUser(User user) {
        if (existsUserByUsername(user.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        user = userRepository.save(user);
        userPerformanceService.initUserPerformance(user);
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
