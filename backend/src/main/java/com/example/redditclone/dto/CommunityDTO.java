package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    @Getter
    @Setter
    public static class Get {
        private String id;
        private String name;
        private String description;
        private LocalDate creationDate;
        private boolean isSuspended;
        private String suspendedReason;

        private List<Long> moderators;

        public Get(String id, String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.creationDate = creationDate;
            this.isSuspended = isSuspended;
            this.suspendedReason = suspendedReason;

        }
    }
}
