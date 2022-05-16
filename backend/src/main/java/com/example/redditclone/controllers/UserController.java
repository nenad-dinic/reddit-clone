package com.example.redditclone.controllers;

import com.example.redditclone.LoadDatabase;
import com.example.redditclone.dto.AddUserData;
import com.example.redditclone.misc.APIResponse;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.time.LocalDate;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;


    @GetMapping(value = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE)
    User getUser(@RequestParam("id") String id) {
        return repository.findById(Long.parseLong(id)).get();
    }

    @PostMapping(value = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    User createUser(@RequestBody AddUserData data){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] MessageDigest = md.digest(data.getPassword().getBytes());
            BigInteger no = new BigInteger(1,MessageDigest);
            String hashPassword = no.toString(16);
            while (hashPassword.length()<32) {
                hashPassword = "0" + hashPassword;
            }
            return repository.save(new User (data.getUsername(), hashPassword, data.getEmail(), data.getAvatar(), data.getRegistrationDate(), data.getDescription(), data.getDisplayName()));

        } catch (Exception e) {
            //throw new APIResponse(1001, "failure", "Failed to register");
            return null;
        }
    }

}
