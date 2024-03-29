package com.example.redditclone.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;

//@Entity
@Getter
@Setter
//@Table(name="post")
@Document(indexName = "post")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Post {
    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String text;
    @Field(type = FieldType.Date)
    private LocalDate creationDate;
    @Field(type = FieldType.Text)
    private String imagePath;
    @Field(type = FieldType.Integer)
    private Long postedBy;
    @Field(type = FieldType.Text)
    private String communityId;
    @Field(type = FieldType.Text)
    private String filePath;
    @Field(type =  FieldType.Text)
    private String fileText;

    public Post() {

    }

    public Post(String title, String text, LocalDate creationDate, String imagePath, Long postedBy, String communityId, String filePath, String fileText) {
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.imagePath = imagePath;
        this.postedBy = postedBy;
        this.communityId = communityId;
        this.filePath = filePath;
        this.fileText = fileText;
    }
}
