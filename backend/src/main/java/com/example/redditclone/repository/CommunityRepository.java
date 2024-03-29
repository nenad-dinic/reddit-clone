package com.example.redditclone.repository;

import com.example.redditclone.model.Community;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CommunityRepository extends ElasticsearchRepository<Community, String> {
    Community findByName(String name);


}
