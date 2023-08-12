package com.example.redditclone.controllers;

import com.example.redditclone.dto.JWTTokenDTO;
import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.dto.UserDTO;
import com.example.redditclone.misc.HashPassword;
import com.example.redditclone.model.Admin;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.AdminRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.ReactionRepository;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    PostRepository postRepository;

    Long getUserKarma(Long userId) {
        List<Post> posts = postRepository.findAllByPostedBy(userId);
        List<String> postIds = new ArrayList<>();
        for (Post p : posts) {
            postIds.add(p.getId());
        }
        return reactionRepository.getKarmaForUser(postIds);
    }

    @GetMapping(value = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO.Get getUser(@RequestParam("id") String id) {
        User u = userRepository.findById(Long.parseLong(id)).get();
        UserDTO.Get result = new UserDTO.Get(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getDescription(), u.getDisplayName(), u.getRegistrationDate(), getUserKarma(u.getId()));
        return result;
    }

    @PostMapping(value = "/user/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO.Get createUser(@RequestBody UserDTO.Add data){
        try {
            String hashPassword = HashPassword.createHash(data.getPassword());
            User u = userRepository.save(new User (data.getUsername(), hashPassword, data.getEmail(), data.getAvatar(), LocalDate.now(), data.getDescription(), data.getDisplayName()));
            UserDTO.Get result = new UserDTO.Get(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getDescription(), u.getDisplayName(), u.getRegistrationDate(), getUserKarma(u.getId()));
            return result;

        } catch (Exception e) {
            //throw new APIResponse(1001, "failure", "Failed to register");
            return null;
        }
    }

    @PostMapping(value = "user/login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    JWTTokenDTO loginUser(@RequestBody UserDTO.Login data) {
        String hashPassword = HashPassword.createHash(data.getPassword());
        try {
            User u = userRepository.findOneByUsernameAndPassword(data.getUsername(), hashPassword);
            if (u != null) {
                Admin a = adminRepository.findOneByUserId(u.getId());
                return new JWTTokenDTO(TokenUtils.generateToken(u.getUsername(), a != null ? "admin" : "user"));
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
    UserDTO.Get changePassword(@RequestBody UserDTO.ChangePassword data) {
        String hashPassword = HashPassword.createHash(data.getPassword());
        String newHashPassword = HashPassword.createHash(data.getNewPassword());
        try {
            User u = userRepository.findOneByUsernameAndPassword(data.getUsername(), hashPassword);
            u.setPassword(newHashPassword);
            userRepository.save(u);
            UserDTO.Get result = new UserDTO.Get(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getDescription(), u.getDisplayName(), u.getRegistrationDate(), getUserKarma(u.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping(value = "user/tokenId",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO.Get tokenId(@RequestBody JWTTokenDTO.UserToken data, HttpServletRequest request) {
        String username = TokenUtils.getUsernameFromToken(data.token);
        try {
            User u = userRepository.findOneByUsername(username);
            UserDTO.Get result = new UserDTO.Get(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getDescription(), u.getDisplayName(), u.getRegistrationDate(), getUserKarma(u.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "user/edit",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    UserDTO.Get editProfile(@RequestBody@RequestParam("id") String id,@RequestBody UserDTO.EditProfile data) {
        try {
            User u = userRepository.findById(Long.parseLong(id)).get();
            u.setDisplayName(data.getDisplayName());
            u.setEmail(data.getEmail());
            u.setDescription(data.getDescription());
            userRepository.save(u);
            UserDTO.Get result = new UserDTO.Get(u.getId(), u.getUsername(), u.getEmail(), u.getAvatar(), u.getDescription(), u.getDisplayName(), u.getRegistrationDate(), getUserKarma(u.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
