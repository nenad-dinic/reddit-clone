package com.example.redditclone.controllers;

import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.model.Post;
import com.example.redditclone.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @PostMapping(value = "/post",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Post createPost(@RequestBody PostDTO.Add data) {
        try {
            return postRepository.save(new Post(data.getTitle(), data.getText(), LocalDate.now(), "", data.getUserId(), data.getCommunityId()));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/post",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Post updatePost(@RequestParam("id") String id,@RequestBody PostDTO.Update data) {
        try {
            Post p = postRepository.findById(Long.parseLong(id)).get();
            p.setTitle(data.getTitle());
            p.setText(data.getText());
            return postRepository.save(p);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/post",
    produces = MediaType.APPLICATION_JSON_VALUE)
    Post deletePost(@RequestParam("id") String id) {
        try {
            Post p =postRepository.findById(Long.parseLong(id)).get();
            postRepository.delete(p);
            return p;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/post",
    produces = MediaType.APPLICATION_JSON_VALUE)
    Post getPost(@RequestParam("id") String id) {
        try {
            return postRepository.findById(Long.parseLong(id)).get();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/posts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<Post> getPosts() {
        try {
            return postRepository.findByOrderByCreationDateDesc();
        } catch (Exception e) {
            return null;
        }
    }
}
