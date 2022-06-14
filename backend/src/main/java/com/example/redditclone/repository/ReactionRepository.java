package com.example.redditclone.repository;

import com.example.redditclone.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query(value = "select * from reaction " +
            "where reaction_by = :reactionBy and " +
            "((:reactionToPost is not null and reaction_to_post_id = :reactionToPost) or " +
            "(:reactionToComment is not null and reaction_to_comment_id = :reactionToComment))",
            nativeQuery = true)
    Reaction findOneReaction(Long reactionBy, Long reactionToPost, Long reactionToComment);

    @Query(value = "SELECT " +
            "(SELECT COUNT(*) FROM reaction WHERE reaction_to_post_id = :postId AND TYPE = 0) - " +
            "(SELECT COUNT(*) FROM reaction WHERE reaction_to_post_id = :postId AND TYPE = 1) AS value " +
            "FROM reaction WHERE reaction_to_post_id = :postId UNION (SELECT 0 FROM reaction LIMIT 1)" +
            "LIMIT 1;", nativeQuery = true)
    Long getKarmaForPost(Long postId);
}
