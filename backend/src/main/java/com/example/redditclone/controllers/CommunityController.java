package com.example.redditclone.controllers;

import com.example.redditclone.dto.CommunityDTO;
import com.example.redditclone.misc.PdfHandler;
import com.example.redditclone.misc.FileHandler;
import com.example.redditclone.model.Post;
import com.example.redditclone.repository.CommunityRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.model.Community;
import com.example.redditclone.model.Moderator;
import com.example.redditclone.repository.ModeratorRepository;
import com.example.redditclone.repository.ReactionRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommunityController {
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    ModeratorRepository moderatorRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Autowired
    PdfHandler pdfHandler;

    @PostMapping(value = "/community",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get createCommunity(@ModelAttribute CommunityDTO.Add data) {
        try {
            String filePath = FileHandler.saveFile(data.getDescPdf());
            File file = new File("." + filePath);
            String fileText = pdfHandler.getText(file);
            Community c = communityRepository.save(new Community(data.getName(), data.getDescription(), LocalDate.now(), false, "", filePath, fileText));
            try {
                moderatorRepository.save(new Moderator(data.getUserId(), c.getId()));
            } catch (Exception e) {
                e.printStackTrace();
                communityRepository.delete(c);
            }
            int postCount = getPostCount(c.getId());
            int totalKarma = getTotalKarma(c.getId());
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason(), c.getFilePath(), postCount, calcKarma(postCount, totalKarma));
            result.setModerators(getCommunityModerators(result.getId()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping(value = "/community",
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get deleteCommunity(@RequestParam("id") String id) {
        try {
            Community c = communityRepository.findById(id).get();
            communityRepository.delete(c);
            int postCount = getPostCount(c.getId());
            int totalKarma = getTotalKarma(c.getId());
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason(), c.getFilePath(), postCount, calcKarma(postCount, totalKarma));
            result.setModerators(getCommunityModerators(result.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/community",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get updateCommunity(@RequestParam("id") String id, @RequestBody CommunityDTO.Update data) {
        try {
            Community c = communityRepository.findById(id).get();
            c.setName(data.getName());
            c.setDescription(data.getDescription());
            communityRepository.save(c);
            int postCount = getPostCount(c.getId());
            int totalKarma = getTotalKarma(c.getId());
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason(), c.getFilePath(), postCount, calcKarma(postCount, totalKarma));
            result.setModerators(getCommunityModerators(result.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/community",
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get getCommunity(@RequestParam("name") String name) {
        try {
            Community c = communityRepository.findByName(name);
            int postCount = getPostCount(c.getId());
            int totalKarma = getTotalKarma(c.getId());
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason(), c.getFilePath(), postCount, calcKarma(postCount, totalKarma));
            result.setModerators(getCommunityModerators(result.getId()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*@GetMapping(value = "/communities",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<CommunityDTO.Get> getCommunities() {
        try {
            Iterable<Community> c =  communityRepository.findAll();
            List<CommunityDTO.Get> result = new ArrayList<>();
            for(Community comm : c) {
                CommunityDTO.Get cDTO = new CommunityDTO.Get(comm.getId(), comm.getName(), comm.getDescription(), comm.getCreationDate(), comm.isSuspended(), comm.getSuspendedReason(), comm.getFilePath());
                cDTO.setModerators(getCommunityModerators(cDTO.getId()));
                result.add(cDTO);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }*/

    @GetMapping(value = "/communities",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<CommunityDTO.Get> getCommunitiesForSearch(@RequestParam("search") String search) {
        QueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(search, "name", "description", "fileText");
        QueryBuilder nameWildcardQuery = QueryBuilders.wildcardQuery("name", "*" + search + "*");
        QueryBuilder descriptionWildcardQuery = QueryBuilders.wildcardQuery("description", "*" + search + "*");
        QueryBuilder fileTextWildcardQuery = QueryBuilders.wildcardQuery("fileText", "*" + search + "*");
        QueryBuilder combinedQuery = QueryBuilders.boolQuery()
                .should(multiMatchQuery)
                .should(nameWildcardQuery)
                .should(descriptionWildcardQuery)
                .should(fileTextWildcardQuery);
        Query query = new NativeSearchQueryBuilder().withFilter(combinedQuery).build();
        SearchHits<Community> communityHits = elasticsearchOperations.search(query, Community.class, IndexCoordinates.of("community"));
        List<Community> communities = new ArrayList<>();
        communityHits.forEach(hit -> {
            communities.add(hit.getContent());
        });
        List<CommunityDTO.Get> result = new ArrayList<>();
        for(Community c : communities) {
            int postCount = getPostCount(c.getId());
            int totalKarma = getTotalKarma(c.getId());
            CommunityDTO.Get cDTO = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason(), c.getFilePath(), postCount, calcKarma(postCount, totalKarma));
            cDTO.setModerators(getCommunityModerators(cDTO.getId()));
            result.add(cDTO);
        }
        return result;
    }

    List<Long> getCommunityModerators(String communityId) {
        List<Moderator> moderators =  moderatorRepository.findAllByCommunityId(communityId);
        List<Long> moderatorIds = new ArrayList<>();
        for (Moderator m : moderators) {
            moderatorIds.add(m.getUserId());
        }
        return moderatorIds;
    }

    int getPostCount(String communityId) {
        int count = postRepository.findAllByCommunityId(communityId).size();
        return count;
    }

    int getTotalKarma(String communityId) {
        int sum = 0;
        List<Post> posts = postRepository.findAllByCommunityId(communityId);
        for (Post p : posts) {
            sum += reactionRepository.getKarmaForPost(p.getId());
        }
        return sum;
    }

    int calcKarma(int postCount, int totalKarma) {
        if (postCount == 0) {
            postCount = 1;
        }
        return totalKarma / postCount;
    }


}
