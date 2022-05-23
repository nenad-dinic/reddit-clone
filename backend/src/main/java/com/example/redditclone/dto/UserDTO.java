package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UserDTO {
    @Getter
    @Setter
    public static class Add {
        private String username;
        private String password;
        private String email;
        private String avatar;
        private String description;
        private String displayName;
    }

    @Getter
    @Setter
    public static class Login {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class ChangePassword {
        private String username;
        private String password;
        private String newPassword;
    }
}