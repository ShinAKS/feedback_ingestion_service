package com.ayush.feedbackingestionservice.pojos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Conversation {
    private String conversationId;
    private String topic;
    private String owner;
    private String postType;
}
