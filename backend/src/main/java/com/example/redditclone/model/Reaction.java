package com.example.redditclone.model;

import com.example.redditclone.enums.ReactionTo;
import com.example.redditclone.enums.ReactionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "reaction")
public class Reaction {
    private @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    private ReactionType type;
    private LocalDate timestamp;
    private Long reactionBy;
    private ReactionTo reactionTo;
    private Long reactionToPostId;
    private Long reactionToCommentId;

    public Reaction() {

    }

    public Reaction(ReactionType type, LocalDate timestamp, Long reactionBy, ReactionTo reactionTo, Long reactionToId) {
        this.type = type;
        this.timestamp = timestamp;
        this.reactionBy = reactionBy;
        this.reactionTo = reactionTo;
        if (reactionTo == ReactionTo.POST) {
            this.reactionToPostId = reactionToId;
        } else {
            this.reactionToCommentId = reactionToId;
        }
    }

}
