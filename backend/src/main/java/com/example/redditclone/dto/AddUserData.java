package com.example.redditclone.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddUserData {
    private String username;
    private String password;
    private String email;
    private String avatar;
    private LocalDate registrationDate;
    private String description;
    private String displayName;
}
