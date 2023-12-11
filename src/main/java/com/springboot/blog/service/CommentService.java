package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long id);

    CommentDto getCommentById(long postId, long commentId);

    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);
}
