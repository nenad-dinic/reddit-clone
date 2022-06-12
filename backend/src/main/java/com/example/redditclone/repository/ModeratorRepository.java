package com.example.redditclone.repository;

import com.example.redditclone.model.Moderator;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
    List<Moderator> findAllByCommunityId(Long communityId);
}
