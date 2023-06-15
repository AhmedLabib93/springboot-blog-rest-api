package com.blog.restapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.restapi.payload.request.PostDto;
import com.blog.restapi.payload.response.PostResponse;
import com.blog.restapi.service.PostService;
import com.blog.restapi.utils.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/v1/posts")
@Tag(name = "CRUD Rest API for Post Resource")
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@Operation(summary = "Create Post Rest API", description = "Create Post Rest API is used to save post into database")
	@ApiResponse(responseCode = "201", description = "Http Status 201 Created")
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}

	@Operation(summary = "GetAll Post Rest API", description = "GetAll Post Rest API is used to get all posts from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}

	@Operation(summary = "Get Post by ID Rest API", description = "Get Post By Id Rest API is used to get post from database by id")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable long postId) {
		return ResponseEntity.ok(postService.getPostById(postId));
	}

	@Operation(summary = "Update Post Rest API", description = "Update Post Rest API is used to update post in database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "postId") long postId) {
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, postId), HttpStatus.OK);
	}

	@Operation(summary = "Delete Post Rest API", description = "Delete Post Rest API is used to remove post from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{postId}")
	public ResponseEntity<String> deletePostById(@PathVariable(name = "postId") long postId) {
		postService.deletePostById(postId);
		return new ResponseEntity<String>("Post entity deleted successfully", HttpStatus.OK);
	}

	@Operation(summary = "Get All Posts by Category ID Rest API", description = "Get All Posts By Category Id Rest API is used to get posts from database by category id")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Long categoryId) {
		List<PostDto> postDtos = postService.getPostsByCategoryId(categoryId);
		return ResponseEntity.ok(postDtos);
	}

}
