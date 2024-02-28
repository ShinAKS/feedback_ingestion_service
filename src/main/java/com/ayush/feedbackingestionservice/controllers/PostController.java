package com.ayush.feedbackingestionservice.controllers;

import com.ayush.feedbackingestionservice.pojos.models.Post;
import com.ayush.feedbackingestionservice.requests.PostRequest;
import com.ayush.feedbackingestionservice.responses.ConversationPosts;
import com.ayush.feedbackingestionservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts/create")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {

        System.out.println("Request : " + postRequest);
        try {
            Post post = postService.handlePostCreation(postRequest);
            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (Exception e) {
            System.out.println("Exception received : " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") String id) {
        try {
            Post post = postService.getPostById(id);
            return ResponseEntity.status(HttpStatus.OK).body(post);
        } catch (Exception e) {
            System.out.println("Exception received : " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPostByTimeRange(@RequestParam(value = "startTime", required = false) String startTime,
                                                     @RequestParam(value = "endTime", required = false) String endTime,
                                                     @RequestParam(value = "postType", required = false) String postType) {

        List<Post> posts = new ArrayList<>();
        LocalDateTime startTimeLocalDateTime = startTime == null? LocalDateTime.MIN : LocalDateTime.parse(startTime);
        LocalDateTime endTimeLocalDateTime = endTime == null ? LocalDateTime.MAX: LocalDateTime.parse(endTime);
        try {
            if (postType == null){
                posts = postService.getPostsWithinTime(startTimeLocalDateTime, endTimeLocalDateTime);
            }
            else{
                posts = postService.getPostsWithinTimeAndType(startTimeLocalDateTime, endTimeLocalDateTime, postType);
            }
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (Exception e) {
            System.out.println("Exception received : " + e.getMessage());
        }
        return null;
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<?> getPostsByConversationId(@PathVariable("id") String conversationId) {
        try {
            ConversationPosts conversationPosts = postService.getPostsByConversationId(conversationId);
            return ResponseEntity.status(HttpStatus.OK).body(conversationPosts);
        } catch (Exception e) {
            System.out.println("Exception received : " + e.getMessage());
        }
        return null;
    }
}
