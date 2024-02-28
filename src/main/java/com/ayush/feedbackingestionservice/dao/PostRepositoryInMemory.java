package com.ayush.feedbackingestionservice.dao;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Conversation;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryInMemory implements IPostRepository{

    Map<String, Post> postMap = new HashMap<>();
    Map<String, Conversation> conversationMap = new HashMap<>();
    Map<String,List<Post>> conversationPosts = new HashMap<>();

    @Override
    public Post savePost(Post post) {
        postMap.put(post.getId(),post);
        if (!conversationPosts.containsKey(post.getConversationId())){
            conversationPosts.put(post.getConversationId(),new ArrayList<>());
            saveNewConversationDetails(post);
        }
        conversationPosts.get(post.getConversationId()).add(post);
        return post;
    }

    private void saveNewConversationDetails(Post post){
        conversationMap.put(post.getConversationId(),
                Conversation.builder()
                        .conversationId(post.getConversationId())
                        .postType(post.getPostType().name())
                        .topic(post.getContent())
                        .owner(post.getPostedBy())
                        .build());

    }


    @Override
    public Post getPostById(String postId) {
        return postMap.get(postId);
    }

    @Override
    public List<Post> findAll() {
        return postMap.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt))
                .toList();
    }

    @Override
    public List<Post> findPostsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        List<Post> allPosts = findAll();
        return allPosts.stream()
                .filter(post -> post.getCreatedAt().isAfter(startTime)  &&  post.getCreatedAt().isBefore(endTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findPostsByTimeRangeAndPostType(LocalDateTime startTime, LocalDateTime endTime, PostType postType) {
        List<Post> postsWithinTimeRange = findPostsByTimeRange(startTime,endTime);
        return postsWithinTimeRange.stream()
                .filter(post -> postType.equals(post.getPostType()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Post> findPostsByConversationId(String conversationId) {

        if (conversationPosts.containsKey(conversationId)){
            return conversationPosts.get(conversationId);
        }

        return new ArrayList<>();
    }

    @Override
    public Conversation findByConversationId(String conversationId) {
        return conversationMap.get(conversationId);
    }
}
