package com.ayush.feedbackingestionservice.pojos.models.client.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

    @JsonProperty("tweet_id")
    private String id;

    private String message;
    private int likes;
    private int views;


}
