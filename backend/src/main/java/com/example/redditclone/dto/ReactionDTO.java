package com.example.redditclone.dto;

import com.example.redditclone.enums.ReactionTo;
import com.example.redditclone.enums.ReactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ReactionDTO {

    @Getter
    @Setter
    public static class Add{
        private ReactionType reactionType;
        private Long reactionBy;
        private ReactionTo reactionTo;
        private String reactionToId;
    }
}
