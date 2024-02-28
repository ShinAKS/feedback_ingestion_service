package com.ayush.feedbackingestionservice.responses;

import com.ayush.feedbackingestionservice.pojos.models.Conversation;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ConversationPosts {

    Conversation conversation;
    List<Post> posts;
}
