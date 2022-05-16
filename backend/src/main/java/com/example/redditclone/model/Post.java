package com.example.redditclone;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
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
    private int postedBy;

    public Post() {

    }

    public Post(String title, String text, LocalDate creationDate, String imagePath, int postedBy) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.imagePath = imagePath;
        this.postedBy = postedBy;
    }
}
