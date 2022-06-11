package com.example.redditclone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTTokenDTO {
    private String token;

    public JWTTokenDTO(String token) {
        this.token = token;
    }

    @Getter
    @Setter
    public static class UserToken {
        public String token;
    }
}
