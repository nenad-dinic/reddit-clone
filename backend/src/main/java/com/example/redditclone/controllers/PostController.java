package com.example.redditclone.controllers;

import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.enums.ReactionTo;
import com.example.redditclone.enums.ReactionType;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Reaction;
import com.example.redditclone.repository.CommunityRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.ReactionRepository;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommunityRepository communityRepository;

    @PostMapping(value = "/post",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    PostDTO.Get createPost(@RequestBody PostDTO.Add data) {
        try {
            Post p = postRepository.save(new Post(data.getTitle(), data.getText(), LocalDate.now(), "", data.getUserId(), data.getCommunityId()));
            reactionRepository.save(new Reaction(ReactionType.UPVOTE, LocalDate.now(), data.getUserId(), ReactionTo.POST, p.getId()));
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get());
            result.setKarma(reactionRepository.getKarmaForPost(p.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/post",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    PostDTO.Get updatePost(@RequestParam("id") String id,@RequestBody PostDTO.Update data) {
        try {
            Post p = postRepository.findById(Long.parseLong(id)).get();
            p.setTitle(data.getTitle());
            p.setText(data.getText());
            postRepository.save(p);
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get());
            result.setKarma(reactionRepository.getKarmaForPost(p.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/post",
    produces = MediaType.APPLICATION_JSON_VALUE)
    PostDTO.Get deletePost(@RequestParam("id") String id) {
        try {
            Post p =postRepository.findById(Long.parseLong(id)).get();
            postRepository.delete(p);
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get());
            result.setKarma(reactionRepository.getKarmaForPost(p.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/post",
    produces = MediaType.APPLICATION_JSON_VALUE)
    PostDTO.Get getPost(@RequestParam("id") String id) {
        try {
            Post p = postRepository.findById(Long.parseLong(id)).get();
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get());
            result.setKarma(reactionRepository.getKarmaForPost(p.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/posts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PostDTO.Get> getPosts() {
        try {
            List<Post> posts = postRepository.findByOrderByCreationDateDesc();
            List<PostDTO.Get> result = new ArrayList<>();
            for(Post p : posts) {
                result.add(new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), reactionRepository.getKarmaForPost(p.getId()),  userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get()));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/posts/community",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PostDTO.Get> getCommunityPosts(@RequestParam("name") String name) {
        try {
            List<Post> posts = postRepository.findAllForCommunity(name);
            List<PostDTO.Get> result = new ArrayList<>();
            for(Post p : posts) {
                result.add(new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), reactionRepository.getKarmaForPost(p.getId()),  userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get()));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
