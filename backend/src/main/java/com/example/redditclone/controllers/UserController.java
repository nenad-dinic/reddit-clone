package com.example.redditclone.controllers;

import com.example.redditclone.dto.JWTToken;
import com.example.redditclone.dto.UserDTO;
import com.example.redditclone.misc.HashPassword;
import com.example.redditclone.model.Admin;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.AdminRepository;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @GetMapping(value = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE)
    User getUser(@RequestParam("id") String id) {
        return userRepository.findById(Long.parseLong(id)).get();
    }

    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    User createUser(@RequestBody UserDTO.Add data){
        try {
            String hashPassword = HashPassword.createHash(data.getPassword());
            return userRepository.save(new User (data.getUsername(), hashPassword, data.getEmail(), data.getAvatar(), LocalDate.now(), data.getDescription(), data.getDisplayName()));

        } catch (Exception e) {
            //throw new APIResponse(1001, "failure", "Failed to register");
            return null;
        }
    }

    @PostMapping(value = "user/login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    JWTToken loginUser(@RequestBody UserDTO.Login data) {
        String hashPassword = HashPassword.createHash(data.getPassword());
        try {
            User u = userRepository.findOneByUsernameAndPassword(data.getUsername(), hashPassword);
            if (u != null) {
                Admin a = adminRepository.findOneByUserId(u.getId());
                return new JWTToken(TokenUtils.generateToken(u.getUsername(), a != null ? "admin" : "user"));
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
            //return null;
        }
    }

    @PutMapping(value = "user/changePassword",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    User changePassword(@RequestBody UserDTO.ChangePassword data) {
        String hashPassword = HashPassword.createHash(data.getPassword());
        String newHashPassword = HashPassword.createHash(data.getNewPassword());
        try {
            User u = userRepository.findOneByUsernameAndPassword(data.getUsername(), hashPassword);
            u.setPassword(newHashPassword);
            return userRepository.save(u);
        } catch (Exception e) {
            return null;
        }
    }

}
