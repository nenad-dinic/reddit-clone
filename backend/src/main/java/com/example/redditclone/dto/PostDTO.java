package com.example.redditclone.dto;

import com.example.redditclone.model.Community;
import com.example.redditclone.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PostDTO {

    @Getter
    @Setter
    public static class Add {
        private Long userId;
        private String communityId;
        private String title;
        private String text;
        private String imagePath;
    }

    @Getter
    @Setter
    public static class Update {
        private String title;
        private String text;
    }

    @Getter
    @Setter
    public static class Get {
        private String id;
        private String title;
        private String text;
        private LocalDate creationDate;
        private String imagePath;
        private Long postedBy;
        private String communityId;
        private Long karma;
        private User user;
        private Community community;

        public Get(String id, String title, String text, LocalDate creationDate, String imagePath, Long postedBy, String communityId, Long karma, User user, Community community) {
            this.id = id;
            this.title = title;
            this.text = text;
            this.creationDate = creationDate;
            this.imagePath = imagePath;
            this.postedBy = postedBy;
            this.communityId = communityId;
            this.karma = karma;
            this.user = user;
            this.community = community;
        }
    }
}
