package com.example.redditclone.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="post")
public class Post {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String title;
    private String text;
    private LocalDate creationDate;
    private String imagePath;
    private Long postedBy;
    private Long communityId;

    public Post() {

    }

    public Post(String title, String text, LocalDate creationDate, String imagePath, Long postedBy, Long communityId) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.imagePath = imagePath;
        this.postedBy = postedBy;
        this.communityId = communityId;
    }
}
