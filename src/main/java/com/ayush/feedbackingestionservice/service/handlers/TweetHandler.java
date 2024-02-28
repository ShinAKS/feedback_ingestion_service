package com.ayush.feedbackingestionservice.service.handlers;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.pojos.models.PostMetadata;
import com.ayush.feedbackingestionservice.pojos.models.client.twitter.Tweet;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.ayush.feedbackingestionservice.service.IPostHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class TweetHandler implements IPostHandler {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Post handlePost(PostRequest postRequest) throws JsonProcessingException {

        Tweet tweet = unmarshalPostToTweet(postRequest.getContent());
        Post post = createPostForTweet(postRequest,tweet);
        return post;
    }

    private Post createPostForTweet(PostRequest postRequest , Tweet tweet) {
        return Post.builder()
                .id(tweet.getId())
                .postedBy(postRequest.getPostedBy())
                .createdAt(LocalDateTime.parse(postRequest.getCreatedAt()))
                .contentMetadata(
                        PostMetadata.builder()
                                .contentSize(tweet.getMessage().length())
                                .language("EN-US")
                                .likes(tweet.getLikes())
                                .views(tweet.getViews())

                        .build())
                .postType(PostType.TWEET)
                .conversationId(Objects.isNull(postRequest.getConversationId())? tweet.getId() : postRequest.getConversationId())
                .content(tweet.getMessage())
                .requestSourceAttribute(postRequest.getRequestSourceAttribute())
                .build();
    }

    private Tweet unmarshalPostToTweet(String content) throws JsonProcessingException {
        Tweet tweet = objectMapper.readValue(content, Tweet.class);
        return tweet;
    }
}
