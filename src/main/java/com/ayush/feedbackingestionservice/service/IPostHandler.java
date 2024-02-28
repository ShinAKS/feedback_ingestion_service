package com.ayush.feedbackingestionservice.service;

import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IPostHandler {

    Post handlePost(PostRequest postRequest) throws JsonProcessingException;

}
