package com.example.redditclone.repository;

import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReposotory extends JpaRepository<Post, Long> {
}
