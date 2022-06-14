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
        private Long communityId;
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
        private Long id;
        private String title;
        private String text;
        private LocalDate creationDate;
        private String imagePath;
        private Long postedBy;
        private Long communityId;
        private Long karma;
        private User user;
        private Community community;

        public Get(Long id, String title, String text, LocalDate creationDate, String imagePath, Long postedBy, Long communityId, Long karma, User user, Community community) {
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
