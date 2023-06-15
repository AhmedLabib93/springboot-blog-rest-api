package com.blog.restapi.service;

import java.util.List;

import com.blog.restapi.payload.request.PostDto;
import com.blog.restapi.payload.response.PostResponse;

public interface PostService {

	public PostDto createPost(PostDto postDto);

	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

	public PostDto getPostById(long id);

	public PostDto updatePost(PostDto postDto, long id);

	public void deletePostById(long id);
	
	public List<PostDto> getPostsByCategoryId(Long categoryId);
}
