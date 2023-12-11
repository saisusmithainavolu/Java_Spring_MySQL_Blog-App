package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag( name = " Rest APIs for Post Resource")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post Rest API
    @Operation( summary = "Create Post Rest API", description = "This Rest API saves the post into database")
    @SecurityRequirement( name = "Authorization Required" )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all posts Rest API
    @Operation( summary = "Get All Posts Rest API", description = "This Rest API retrieves all the post from database")
    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    // Get post by ID Rest API
    @Operation( summary = "Get Post Rest API",description = "This Rest API retrieves the specified post from database")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }

    // update blog post Rest API
    @Operation( summary = "Update Post Rest API",description = "This Rest API updates the specified post in the database")
    @SecurityRequirement( name = "Authorization Required" )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id){
        PostDto postResponse = postService.updatePostById(postDto,id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // delete blog post Rest API
    @Operation( summary = "Delete Post Rest API",description = "This Rest API deletes the specified post in the database")
    @SecurityRequirement( name = "Authorization Required" )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully", HttpStatus.OK);
    }


}
