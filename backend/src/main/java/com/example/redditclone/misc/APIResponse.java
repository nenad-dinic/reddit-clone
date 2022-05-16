package com.example.redditclone.misc;

public class APIResponse {
    private int statusCode;
    private String status;
    private String message;

    public APIResponse(int statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"statusCode\":" + "\""+this.statusCode+"\"," +
                "\"status\":" + "\""+this.status+"\"," +
                "\"message\":" + "\""+this.message+"\"";
    }
}
