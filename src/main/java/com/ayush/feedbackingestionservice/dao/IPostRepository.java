package com.ayush.feedbackingestionservice.dao;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Conversation;
import com.ayush.feedbackingestionservice.pojos.models.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface IPostRepository {

    Post savePost(Post post);

    Post getPostById(String postId);

    List<Post> findAll();

    List<Post> findPostsByTimeRange(LocalDateTime startTime, LocalDateTime endTime);

    List<Post> findPostsByTimeRangeAndPostType(LocalDateTime startTime, LocalDateTime endTime , PostType postType);

    List<Post> findPostsByConversationId(String conversationId);

    Conversation findByConversationId(String conversationId);
}
