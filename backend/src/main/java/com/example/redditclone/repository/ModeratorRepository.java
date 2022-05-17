package com.example.redditclone.repository;

import com.example.redditclone.model.Moderator;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
}
