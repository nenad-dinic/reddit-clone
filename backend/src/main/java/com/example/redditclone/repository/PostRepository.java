package com.example.redditclone.repository;

import com.example.redditclone.model.Post;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends ElasticsearchRepository<Post, Long> {
    List<Post> findByOrderByCreationDateDesc();

    //@Query(value = "SELECT p.* from post p inner join Community on p.community_id = Community.id where Community.name = :name order by creation_date desc")
    //List<Post> findAllForCommunity(String name);

    //@Query("{\"bool\": {\"must\": [{\"term\": {\"community.name\": \"?0\"}}]}}")
    List<Post> findAllByCommunityId(@Param("communityId") String id);

    List<Post> findAllByPostedBy(@Param("postedBy") Long postedBy);
}
