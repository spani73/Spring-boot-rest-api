package com.springboot.app.service;


import com.springboot.app.payload.PostDto;
import com.springboot.app.payload.PostResponse;


public interface PostService {
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo , int pageSize,String sortBy, String sortDir);
	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto,long id);
	
	void deletePostById(long id);
}
