package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class CommunityDTO {
    @Getter
    @Setter
    public static class Add {
        private Long userId;
        private String name;
        private String description;
        private MultipartFile descPdf;
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
        private String filePath;
        private List<Long> moderators;
        private int postCount;
        private int averageKarma;

        public Get(String id, String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason, String filePath, int postCount, int averageKarma) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.creationDate = creationDate;
            this.isSuspended = isSuspended;
            this.suspendedReason = suspendedReason;
            this.filePath = filePath;
            this.postCount = postCount;
            this.averageKarma = averageKarma;

        }
    }
}
