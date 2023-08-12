package com.example.redditclone.controllers;

import com.example.redditclone.dto.CommunityDTO;
import com.example.redditclone.repository.CommunityRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.model.Community;
import com.example.redditclone.model.Moderator;
import com.example.redditclone.repository.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/community",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get createCommunity(@RequestBody CommunityDTO.Add data) {
        try {
            Community c = communityRepository.save(new Community(data.getName(), data.getDescription(), LocalDate.now(), false, ""));
            try {
                moderatorRepository.save(new Moderator(data.getUserId(), c.getId()));
            } catch (Exception e) {
                communityRepository.delete(c);
            }
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason());
            result.setModerators(getCommunityModerators(result.getId()));
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping(value = "/community",
    produces = MediaType.APPLICATION_JSON_VALUE)
    CommunityDTO.Get deleteCommunity(@RequestParam("id") String id) {
        try {
            Community c = communityRepository.findById(id).get();
            communityRepository.delete(c);
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason());
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
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason());
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
            CommunityDTO.Get result = new CommunityDTO.Get(c.getId(), c.getName(), c.getDescription(), c.getCreationDate(), c.isSuspended(), c.getSuspendedReason());
            result.setModerators(getCommunityModerators(result.getId()));
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/communities",
    produces = MediaType.APPLICATION_JSON_VALUE)
    List<CommunityDTO.Get> getCommunities() {
        try {
            Iterable<Community> c =  communityRepository.findAll();
            List<CommunityDTO.Get> result = new ArrayList<>();
            for(Community comm : c) {
                CommunityDTO.Get cDTO = new CommunityDTO.Get(comm.getId(), comm.getName(), comm.getDescription(), comm.getCreationDate(), comm.isSuspended(), comm.getSuspendedReason());
                cDTO.setModerators(getCommunityModerators(cDTO.getId()));
                result.add(cDTO);

            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    List<Long> getCommunityModerators(String communityId) {
        List<Moderator> moderators =  moderatorRepository.findAllByCommunityId(communityId);
        List<Long> moderatorIds = new ArrayList<>();
        for (Moderator m : moderators) {
            moderatorIds.add(m.getUserId());
        }
        return moderatorIds;
    }
}
