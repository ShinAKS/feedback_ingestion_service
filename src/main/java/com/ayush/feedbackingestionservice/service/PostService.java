package com.ayush.feedbackingestionservice.service;

import com.ayush.feedbackingestionservice.dao.IPostRepository;
import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Conversation;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.ayush.feedbackingestionservice.responses.ConversationPosts;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final IPostHandler postHandler;

    private final IPostRepository postRepository;


    @Autowired
    public PostService(IPostHandler postHandler, IPostRepository postRepository){

        this.postHandler = postHandler;
        this.postRepository = postRepository;
    }

    public Post handlePostCreation(PostRequest postRequest) throws JsonProcessingException {
        Post post = postHandler.handlePost(postRequest);
        postRepository.savePost(post);

        return post;
    }

    public List<Post> getPostsWithinTime(LocalDateTime startTime, LocalDateTime endTime){
        return postRepository.findPostsByTimeRange(startTime,endTime);
    }

    public List<Post> getPostsWithinTimeAndType(LocalDateTime startTime, LocalDateTime endTime, String type){
        PostType postType = PostType.valueOf(type);
        return postRepository.findPostsByTimeRangeAndPostType(startTime,endTime,postType);
    }

    public Post getPostById(String id){
        return postRepository.getPostById(id);
    }

    public ConversationPosts getPostsByConversationId(String conversationId){
        Conversation conversation = postRepository.findByConversationId(conversationId);
        List<Post> posts = postRepository.findPostsByConversationId(conversationId);

        return ConversationPosts.builder()
                .conversation(conversation)
                .posts(posts)
                .build();
    }


}
