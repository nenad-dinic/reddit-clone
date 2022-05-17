package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordData {
    private String username;
    private String password;
    private String newPassword;
}
