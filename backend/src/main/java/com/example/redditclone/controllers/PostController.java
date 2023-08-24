package com.example.redditclone.controllers;

import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.misc.PdfHandler;
import com.example.redditclone.misc.FileHandler;
import com.example.redditclone.repository.CommunityRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.enums.ReactionTo;
import com.example.redditclone.enums.ReactionType;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Reaction;
import com.example.redditclone.repository.ReactionRepository;
import com.example.redditclone.repository.UserRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    PdfHandler pdfHandler;

    @PostMapping(value = "/post",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    PostDTO.Get createPost(@ModelAttribute PostDTO.Add data) {
        try {
            String filePath = FileHandler.saveFile(data.getFilePdf());
            File file = new File("." + filePath);
            String fileText = pdfHandler.getText(file);
            Post p = postRepository.save(new Post(data.getTitle(), data.getText(), LocalDate.now(), "", data.getUserId(), data.getCommunityId(), filePath, fileText));
            reactionRepository.save(new Reaction(ReactionType.UPVOTE, LocalDate.now(), data.getUserId(), ReactionTo.POST, p.getId()));
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath());
            result.setKarma(reactionRepository.getKarmaForPost(p.getId()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
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
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath());
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
            Post p = postRepository.findById(Long.parseLong(id)).get();
            postRepository.delete(p);
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath());
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
            PostDTO.Get result = new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), 0L, userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath());
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
                result.add(new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), reactionRepository.getKarmaForPost(p.getId()),  userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath()));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/posts/community",
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PostDTO.Get> getCommunityPosts(@RequestParam("communityId") String communityId) {
        try {
            List<Post> posts = postRepository.findAllByCommunityId(communityId);
            List<PostDTO.Get> result = new ArrayList<>();
            for(Post p : posts) {
                result.add(new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), reactionRepository.getKarmaForPost(p.getId()),  userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath()));
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/posts/search",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<PostDTO.Get> getPostsForSearch(@RequestParam("search") String search, @RequestParam("from") long from, @RequestParam("to") long to) {
        QueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(search, "title", "text", "fileText");
        QueryBuilder nameWildcardQuery = QueryBuilders.wildcardQuery("title", "*" + search + "*");
        QueryBuilder descriptionWildcardQuery = QueryBuilders.wildcardQuery("text", "*" + search + "*");
        QueryBuilder fileTextWildcardQuery = QueryBuilders.wildcardQuery("fileText", "*" + search + "*");
        QueryBuilder combinedQuery = QueryBuilders.boolQuery()
                .should(multiMatchQuery)
                .should(nameWildcardQuery)
                .should(descriptionWildcardQuery)
                .should(fileTextWildcardQuery);
        Query query = new NativeSearchQueryBuilder().withFilter(combinedQuery).build();
        SearchHits<Post> postHits = elasticsearchOperations.search(query, Post.class, IndexCoordinates.of("post"));
        List<Post> posts = new ArrayList<>();
        postHits.forEach(hit -> {
            posts.add(hit.getContent());
        });
        List<PostDTO.Get> result = new ArrayList<>();
        for (Post p : posts) {
            long karma = reactionRepository.getKarmaForPost(p.getId());
            if (karma > from && karma < to) {
                result.add(new PostDTO.Get(p.getId(), p.getTitle(), p.getText(), p.getCreationDate(), p.getImagePath(), p.getPostedBy(), p.getCommunityId(), karma,  userRepository.findById(p.getPostedBy()).get(), communityRepository.findById(p.getCommunityId()).get(), p.getFilePath()));
            }
        }
        return result;
    }
}
