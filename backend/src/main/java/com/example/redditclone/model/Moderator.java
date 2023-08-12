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
    private String communityId;

    public Moderator(Long userId, String communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public Moderator() {

    }
}
