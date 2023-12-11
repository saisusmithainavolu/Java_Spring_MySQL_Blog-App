package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/")
@Tag( name = " Rest APIs for Comment Resource")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation( summary = "Create Comment Rest API", description = "This Rest API saves the comment into database")
    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,@Valid @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @Operation( summary = "Get All Comments Of a Post Rest API", description = "This Rest API retrieves all the comments of a specified post from the database")
    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getAllComments(@PathVariable(value = "postId") long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    @Operation( summary = "Get Comment Of a Post Rest API", description = "This Rest API retrieves a specific comment of a specified post from the database")
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @Operation( summary = "Update Comment Rest API", description = "This Rest API updates the specified comment in the database")
    @PutMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto), HttpStatus.OK);
    }

    @Operation( summary = "Delete Comment Rest API", description = "This Rest API deletes the specified comment in the database")
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId, @PathVariable(value = "commentId") long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
    }




}
