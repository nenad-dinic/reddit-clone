package com.example.redditclone.controllers;

import com.example.redditclone.dto.CommunityDTO;
import com.example.redditclone.model.Community;
import com.example.redditclone.model.Moderator;
import com.example.redditclone.model.Post;
import com.example.redditclone.repository.CommunityRepository;
import com.example.redditclone.repository.ModeratorRepository;
import com.example.redditclone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class CommunityController {
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    ModeratorRepository moderatorRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping(value = "/community",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Community createCommunity(@RequestBody CommunityDTO.Add data) {
        try {
            Community c = communityRepository.save(new Community(data.getName(), data.getDescription(), LocalDate.now(), false, ""));
            moderatorRepository.save(new Moderator(data.getUserId(), c.getId()));
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/community",
    produces = MediaType.APPLICATION_JSON_VALUE)
    Community deleteCommunity(@RequestParam("id") String id) {
        try {
            Community c = communityRepository.findById(Long.parseLong(id)).get();
            communityRepository.delete(c);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/community",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Community updateCommunity(@RequestParam("id") String id, @RequestBody CommunityDTO.Update data) {
        try {
            Community c = communityRepository.findById(Long.parseLong(id)).get();
            c.setName(data.getName());
            c.setDescription(data.getDescription());
            return communityRepository.save(c);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/community",
    produces = MediaType.APPLICATION_JSON_VALUE)
    Community getCommunity(@RequestParam("name") String name) {
        try {
            return communityRepository.findByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/communities",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<Community> getCommunities() {
        try {
            return communityRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/community/posts",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<Post> getCommunityPosts(@RequestParam("name") String name) {
        try {
            return postRepository.findAllForCommunity(name);
        } catch (Exception e) {
            return null;
        }
    }
}
