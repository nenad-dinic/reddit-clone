package com.example.redditclone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class TestController {
    @Autowired
    UserRepository repository;


    @GetMapping("/")
    String getHello() {
        return "{'status': 'OK'}";
    }

    @PostMapping("/")
    User createUser() {
        return repository.save(new User ("test", "test", "test", "test", LocalDate.now(), "test", "test"));
    }
}
