package com.ayush.feedbackingestionservice.service;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.ayush.feedbackingestionservice.service.handlers.Intercom;
import com.ayush.feedbackingestionservice.service.handlers.ZendeskHandler;
import com.ayush.feedbackingestionservice.service.handlers.TweetHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class PostHandlerWrapper implements IPostHandler{

    Map<PostType,IPostHandler> postHandlerMap = new HashMap<>();


    @Autowired
    public ZendeskHandler zendeskHandler;

    @Autowired
    public TweetHandler tweetHandler;

    @PostConstruct
    public void setup(){
        postHandlerMap.put(PostType.ZENDESK, zendeskHandler);
        postHandlerMap.put(PostType.TWEET, tweetHandler);
    }


    @Override
    public Post handlePost(PostRequest postRequest) throws JsonProcessingException {
        Post post = postHandlerMap.get(PostType.getPostType(postRequest.getPostType())).handlePost(postRequest);
        return post;
    }
}
