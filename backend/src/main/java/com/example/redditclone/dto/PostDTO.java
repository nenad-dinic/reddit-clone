package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

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
}
