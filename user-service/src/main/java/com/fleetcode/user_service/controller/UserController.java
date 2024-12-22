package com.fleetcode.user_service.controller;

import com.fleetcode.user_service.dto.UpdateUserPerformanceDTO;
import com.fleetcode.user_service.model.User;
import com.fleetcode.user_service.model.UserPerformance;
import com.fleetcode.user_service.service.UserPerformanceService;
import com.fleetcode.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserPerformanceService userPerformanceService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserPerformanceService userPerformanceService) {
        this.userService = userService;
        this.userPerformanceService = userPerformanceService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error occurred while creating the user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/performance/{userId}")
    public ResponseEntity<Optional<UserPerformance>> getUserPerformance(@PathVariable("userId") Long userId) {
        try {
            Optional<UserPerformance> userPerformance = userPerformanceService.getUserPerformance(userId);
            if (userPerformance.isPresent()) {
                return ResponseEntity.ok(userPerformance);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty());
            }
        } catch (Exception e) {
            logger.error("Error occurred while retrieving user performance: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty());
        }
    }

    @PostMapping("/performance")
    public ResponseEntity<Optional<UserPerformance>> updateUserPerformance(
            @RequestBody UpdateUserPerformanceDTO updateUserPerformanceDTO) {
        try {
            Optional<UserPerformance> updatedPerformance = userPerformanceService.updateUserPerformance(
                    updateUserPerformanceDTO.getUserId(),
                    updateUserPerformanceDTO.getWasAccepted(),
                    updateUserPerformanceDTO.getWasNewQuestion()
            );

            if (updatedPerformance.isPresent()) {
                return ResponseEntity.ok(updatedPerformance); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.empty()); // 404 NOT FOUND
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating the user performance: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Optional.empty()); // 500 INTERNAL SERVER ERROR
        }
    }
}
