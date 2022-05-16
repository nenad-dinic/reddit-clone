package com.example.redditclone.controllers;

import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;


    @GetMapping("/user")
    String getHello() {
        return "{'status': 'OK'}";
    }

    @PostMapping("/user")
    User createUser() {
        return repository.save(new User ("test", "test", "test", "test", LocalDate.now(), "test", "test"));
    }
}
