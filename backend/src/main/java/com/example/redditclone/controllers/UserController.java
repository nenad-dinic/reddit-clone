package com.example.redditclone.controllers;

import com.example.redditclone.LoadDatabase;
import com.example.redditclone.dto.AddUserData;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;


    @GetMapping(value = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE)
    String getHello() {
        return "{\"status\": \"OK\"}";
    }

    @PostMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    User createUser(@RequestBody AddUserData data) {
        try {
            return repository.save(new User (data.getUsername(), data.getPassword(), data.getEmail(), data.getAvatar(), data.getRegistrationDate(), data.getDescription(), data.getDisplayName()));

        } catch (Exception e) {
            return null;
        }
    }
}
