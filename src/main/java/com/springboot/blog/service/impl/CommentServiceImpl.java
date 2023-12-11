package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.APIExceptionHandler;
import com.springboot.blog.exception.ResourceNotFoundExceptionHandler;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public CommentDto createComment(long id,CommentDto commentDto) {

        // convert dto too entity
        Comment comment = mapToEntity(commentDto);

        //retrieve post by Id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",id));

        // set post to comment
        comment.setPost(post);

        // save the entity to database
        Comment newComment = commentRepository.save(comment);

        //convert entity to dto
        CommentDto commentResponse = mapToDTO(newComment);

        //return dto
        return commentResponse;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long id) {
        List<Comment> comments = commentRepository.findByPostId(id);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {

        // retrieve post by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",postId));
        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new APIExceptionHandler(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId,CommentDto commentDto) {
        // retrieve post by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",postId));
        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new APIExceptionHandler(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        // set the comment object
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());

        //save the updated comment into database
        Comment updatedComment = commentRepository.save(comment);
        //return the updated comment to database
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        // retrieve post by ID
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",postId));
        // retrieve comment by ID
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new APIExceptionHandler(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDTO(Comment comment){

        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();//
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = modelMapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();//
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
