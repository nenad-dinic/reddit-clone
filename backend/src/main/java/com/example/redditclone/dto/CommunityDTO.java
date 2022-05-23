package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

public class CommunityDTO {
    @Getter
    @Setter
    public static class Add {
        private Long userId;
        private String name;
        private String description;
    }

    @Getter
    @Setter
    public static class Update {
        private String name;
        private String description;
    }
}
