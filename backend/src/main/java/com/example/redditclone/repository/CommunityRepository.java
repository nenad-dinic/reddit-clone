package com.example.redditclone.repository;

import com.example.redditclone.model.Community;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CommunityRepository extends ElasticsearchRepository<Community, Long> {
    Community findByName(String name);

}
