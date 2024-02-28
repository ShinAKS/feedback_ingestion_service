package com.ayush.feedbackingestionservice.pojos.models;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

    String id;
    String conversationId;
    String postedBy;
    LocalDateTime createdAt;
    PostType postType;
    String content;
    PostMetadata contentMetadata;
    RequestSourceAttribute requestSourceAttribute;
}
