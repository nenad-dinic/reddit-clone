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
//@Table(name = "community")
@Document(indexName = "community")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Community {
    @Id
    @Field(type = FieldType.Text)
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Date)
    private LocalDate creationDate;
    @Field(type = FieldType.Boolean)
    private boolean isSuspended;
    @Field(type = FieldType.Text)
    private String suspendedReason;
    @Field(type = FieldType.Text)
    private String filePath;

    public Community() {

    }

    public Community(String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason, String filePath) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
        this.filePath = filePath;
    }
}
