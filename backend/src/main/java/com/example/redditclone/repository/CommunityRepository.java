package com.example.redditclone.repository;

import com.example.redditclone.model.Community;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Community findByName(String name);
}
