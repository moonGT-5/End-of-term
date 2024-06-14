package org.example.backend.controller;

import org.example.backend.entity.User;
import org.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id:[0-9]+}")
    public ResponseEntity<User> findUserById(@PathVariable int id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users/{name:[A-Za-z]+|[\\u4e00-\\u9fa5]+}")
    public ResponseEntity<User> findUserByName(@PathVariable String name) {
        User user = userService.findUserByName(name);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user) {
        User insertUser = userService.insertUser(user);
        return ResponseEntity.ok(insertUser);
    }

    @PostMapping("/user/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUserById(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
