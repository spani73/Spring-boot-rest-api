package com.springboot.app.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.springboot.app.entity.Post;
import com.springboot.app.exception.ResourceNotFoundException;
import com.springboot.app.payload.PostDto;
import com.springboot.app.payload.PostResponse;
import com.springboot.app.repository.PostRepository;
import com.springboot.app.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepository , ModelMapper mapper) {
		super();
		this.postRepository = postRepository;
		this.mapper = mapper;
	}


	@Override
	public PostDto createPost(PostDto postDto) {
		
		
		Post post = mapToEntity(postDto);
		
		Post newPost = postRepository.save(post);
		
		PostDto postResponse = mapToDTO(newPost);
		
		return postResponse;
	}

    @Override
	public PostResponse getAllPosts(int pageNo , int pageSize , String sortBy, String sortDir) {
		// TODO Auto-generated method stub
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
    	Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
    	
		Page<Post> posts= postRepository.findAll(pageable);
		
		//get content from page object
		List<Post> listOfPosts = posts.getContent();
		List<PostDto> content = listOfPosts.stream().map(post->mapToDTO(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        
        return postResponse;
	}
	
	//convert Entity into Dto
	private PostDto mapToDTO(Post post) {
		
		
		PostDto postDto = mapper.map(post, PostDto.class);
		//using model mapper instead of manually doing it
//		PostDto postDto = new PostDto();
//		postDto.setId(post.getId());
//		postDto.setTitle(post.getTitle());
//		postDto.setDescription(post.getDescription());
//		postDto.setContent(post.getContent());
		return postDto;
	}
	//converted dto to entity
	private Post mapToEntity(PostDto postDto) {
		
		Post post = mapper.map(postDto, Post.class);
		
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());
		return post;
	}


	@Override
	public PostDto getPostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}


	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// TODO Auto-generated method stub
		//get post by id from db
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		Post updatedPost = postRepository.save(post);
		return mapToDTO(updatedPost);
	}


	@Override
	public void deletePostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}
}
