package com.example.redditclone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="moderator")
public class Moderator {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private Long userId;
    private Long communityId;

    public Moderator(Long userId, Long communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public Moderator() {

    }
}
