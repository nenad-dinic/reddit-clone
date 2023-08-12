package com.example.redditclone.controllers;

import com.example.redditclone.dto.ReactionDTO;
import com.example.redditclone.enums.ReactionTo;
import com.example.redditclone.model.Reaction;
import com.example.redditclone.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ReactionController {
    @Autowired
    ReactionRepository reactionRepository;

    @PostMapping(value = "/reaction",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    Reaction AddReaction(@RequestBody ReactionDTO.Add data) {
        Reaction r = reactionRepository.findOneReaction(
                data.getReactionBy(),
                data.getReactionTo() == ReactionTo.POST ? data.getReactionToId() : null,
                data.getReactionTo() == ReactionTo.COMMENT ? Long.valueOf(data.getReactionToId()) : null
        );
        if(r == null){
            try {
                return reactionRepository.save(new Reaction(data.getReactionType(), LocalDate.now(), data.getReactionBy(), data.getReactionTo(), data.getReactionToId()));
            } catch (Exception e) {
                return null;
            }
        }else {
            if(r.getType() == data.getReactionType()){
                try {
                    reactionRepository.delete(r);
                    return r;
                } catch(Exception e){
                    return null;
                }
            }else {
                r.setType(data.getReactionType());
                try {
                    return reactionRepository.save(r);
                }catch(Exception e){
                    return null;
                }
            }
        }

    }
}
