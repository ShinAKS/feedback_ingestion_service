package com.ayush.feedbackingestionservice.service.handlers;

import com.ayush.feedbackingestionservice.pojos.enums.PostType;
import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.pojos.models.PostMetadata;
import com.ayush.feedbackingestionservice.pojos.models.client.zendesk.ZendeskTicket;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.ayush.feedbackingestionservice.service.IPostHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class ZendeskHandler implements IPostHandler {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Post handlePost(PostRequest postRequest) throws JsonProcessingException {

        ZendeskTicket zendeskTicket = unmarshalPostToZendeskTicket(postRequest.getContent());
        Post post = createPostForZendeskTicket(postRequest,zendeskTicket);
        return post;
    }

    private Post createPostForZendeskTicket(PostRequest postRequest , ZendeskTicket zendeskTicket) {
        return Post.builder()
                .id(zendeskTicket.getId())
                .postedBy(postRequest.getPostedBy())
                .createdAt(LocalDateTime.parse(postRequest.getCreatedAt()))
                .contentMetadata(
                        PostMetadata.builder()
                                .contentSize(zendeskTicket.getTicket().getComment().getBody().length())
                                .language("EN-US")
                                .build())
                .postType(PostType.ZENDESK)
                .conversationId(Objects.isNull(postRequest.getConversationId())? zendeskTicket.getId() : postRequest.getConversationId())
                .content(zendeskTicket.getTicket().getComment().getBody())
                .requestSourceAttribute(postRequest.getRequestSourceAttribute())
                .build();
    }

    private ZendeskTicket unmarshalPostToZendeskTicket(String content) throws JsonProcessingException {
        ZendeskTicket zendeskTicket = objectMapper.readValue(content, ZendeskTicket.class);
        return zendeskTicket;
    }
}
