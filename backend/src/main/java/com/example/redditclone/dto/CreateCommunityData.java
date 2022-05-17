package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommunityData {
    private Long userId;
    private String name;
    private String description;
}
