package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2,message = "Title of the post should have atleast 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10,message = "Description of the post should have atleast 10 characters")
    private String description;

    @NotEmpty
    private String content;

    public Set<CommentDto> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDto> comments) {
        this.comments = comments;
    }

    private Set<CommentDto> comments;

    public PostDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
