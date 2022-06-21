package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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

    @Getter
    @Setter
    public static class EditProfile {
        private String displayName;
        private String description;
        private String email;
    }

    @Getter
    @Setter
    public static class Get {
        private Long id;
        private String username;
        private String email;
        private String avatar;
        private String description;
        private String displayName;
        private LocalDate registrationDate;
        private Long karma;

        public Get() {

        }

        public Get(Long id, String username, String email, String avatar, String description, String displayName, LocalDate registrationDate, Long karma) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.avatar = avatar;
            this.description = description;
            this.displayName = displayName;
            this.registrationDate = registrationDate;
            this.karma = karma;

        }
    }
}