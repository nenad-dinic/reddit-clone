package com.example.redditclone.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private LocalDate registrationDate;
    private String description;
    private String displayName;

    public User() {
    }

    public User(String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.registrationDate = registrationDate;
        this.description = description;
        this.displayName = displayName;
    }

}
