package com.ayush.feedbackingestionservice.pojos.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum PostType {

    TWEET("TWEET"),
    ZENDESK("ZENDESK"),
    INTERCOM("INTERCOM");

    private String value;

    public static PostType getPostType(String value){
        return PostType.valueOf(value.toUpperCase());
    }
}
