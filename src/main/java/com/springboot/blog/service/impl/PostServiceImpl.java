package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundExceptionHandler;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity
        Post post = mapToEntity(postDto);

        // save or insert the post entity into database
        Post newPost = postRepository.save(post);

        // sending post details to Rest API using postDto
        //convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

    }

    @Override
    public PostDto getPostById(long id) {

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {

        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",id));

        // set the post object
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // save the updated post object into the database
        Post updatedPost = postRepository.save(post);
        //return the updated post to controller layer
        return mapToDTO(updatedPost);

    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionHandler("Post","id",id));
        postRepository.deleteById(id);

    }

    //convert entity to DTO
    private PostDto mapToDTO(Post post){

        PostDto postDto = modelMapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    //convert DTO to entity
    private Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();//
//        // get title from postDto and set it to post object
//        post.setTitle(postDto.getTitle());//
//        // get description from postDto and set it to post object
//        post.setDescription(postDto.getDescription());//
//        // get content from postDto and set it to post object
//        post.setContent(postDto.getContent());
        return post;
    }
}
