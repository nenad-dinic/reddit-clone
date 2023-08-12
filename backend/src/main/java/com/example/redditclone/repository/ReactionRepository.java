package com.example.redditclone.repository;

import com.example.redditclone.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    @Query(value = "select * from reaction " +
            "where reaction_by = :reactionBy and " +
            "((:reactionToPost is not null and reaction_to_post_id = :reactionToPost) or " +
            "(:reactionToComment is not null and reaction_to_comment_id = :reactionToComment))",
            nativeQuery = true)
    Reaction findOneReaction(Long reactionBy, String reactionToPost, Long reactionToComment);

    @Query(value = "SELECT " +
            "(SELECT COUNT(*) FROM reaction WHERE reaction_to_post_id = :postId AND TYPE = 0) - " +
            "(SELECT COUNT(*) FROM reaction WHERE reaction_to_post_id = :postId AND TYPE = 1) AS value " +
            "FROM reaction LIMIT 1;", nativeQuery = true)
    Long getKarmaForPost(String postId);

    /*@Query(value = "SELECT (" +
            "SELECT COUNT(*) FROM user u " +
            "INNER JOIN post p ON u.id = p.posted_by " +
            "INNER JOIN reaction r ON p.id = r.reaction_to_post_id " +
            "WHERE u.id = :id AND r.type = 0) - " +
            "(SELECT COUNT(*) FROM user u " +
            "INNER JOIN post p ON u.id = p.posted_by " +
            "INNER JOIN reaction r ON p.id = r.reaction_to_post_id " +
            "WHERE u.id = :id AND r.type = 1) " +
            "FROM user u WHERE u.id = :id ; ", nativeQuery = true)*/
    //Long getKarmaForUser(Long id);

    @Query(value = "SELECT (SELECT COUNT(*) " +
            "FROM reaction " +
            "WHERE reaction.reaction_to_post_id IN :postIds " +
            "AND reaction.type = 0) - " +
            "(SELECT COUNT(*) " +
            "FROM reaction " +
            "WHERE reaction.reaction_to_post_id IN :postIds " +
            "AND reaction.type = 1);", nativeQuery = true)
    Long getKarmaForUser(List<String> postIds);
}
