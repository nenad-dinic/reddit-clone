package com.example.redditclone.repository;

import com.example.redditclone.model.Community;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOrderByCreationDateDesc();

    @Query(value = "SELECT p.* from post p inner join Community on p.community_id = Community.id where Community.name = :name order by creation_date desc", nativeQuery = true)
    List<Post> findAllForCommunity(String name);
}
