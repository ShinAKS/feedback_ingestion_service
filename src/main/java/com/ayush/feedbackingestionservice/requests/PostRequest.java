package com.ayush.feedbackingestionservice.requests;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.RequestSourceAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostRequest {

    @Nullable
    String conversationId;
    String postedBy;
    String createdAt;
    String postType;
    @JsonProperty("content")
    String content;
    @JsonProperty("requestSourceAttribute")
    RequestSourceAttribute requestSourceAttribute;

}
