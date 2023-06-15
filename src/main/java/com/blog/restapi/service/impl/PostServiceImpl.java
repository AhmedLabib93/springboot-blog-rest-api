package com.blog.restapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.restapi.entity.Category;
import com.blog.restapi.entity.Post;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.payload.request.PostDto;
import com.blog.restapi.payload.response.PostResponse;
import com.blog.restapi.repository.CategoryRepository;
import com.blog.restapi.repository.PostRepository;
import com.blog.restapi.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;

	public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository,
			ModelMapper modelMapper) {
		super();
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		Post post = modelMapper.map(postDto, Post.class);
		post.setCategory(category);
		postRepository.save(post);
		// convert entity to DTO
		PostDto response = modelMapper.map(post, PostDto.class);
		return response;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts = postRepository.findAll(pageable);
		List<Post> list = posts.getContent();
		List<PostDto> content = list.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(pageNo);
		postResponse.setPageSize(pageSize);
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(long id) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		// post.setComments(postDto.getComments().stream().map((comment)->modelMapper.map(comment,
		// Comment.class)).collect(Collectors.toSet()));
		return modelMapper.map(postRepository.save(post), PostDto.class);
	}

	@Override
	public void deletePostById(long id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<PostDto> getPostsByCategoryId(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		List<Post> posts = postRepository.findPostByCategoryId(categoryId);

		return posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}
}
