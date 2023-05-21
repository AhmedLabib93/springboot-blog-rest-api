package com.blog.restapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.restapi.payload.CommentDto;
import com.blog.restapi.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name="CRUD Rest API for Comment Resource")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	@Operation(summary = "Create Comment Rest API", description = "Create Comment Rest API is used to save comment into database")
	@ApiResponse(responseCode = "201", description = "Http Status 201 Created")
	@SecurityRequirement(name = "Bear Authentication")
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long id,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<CommentDto>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
	}

	@Operation(summary = "GetAll Comments Rest API", description = "GetAll Comments Rest API is used to get all comments for post from database")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getAllComments(@PathVariable long postId) {
		return commentService.getCommentsByPostId(postId);
	}

	@Operation(summary = "Get Comment by ID Rest API", description = "Get Comment By Id Rest API is used to get comment from database by id")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
		return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	@Operation(summary = "Update Comment Rest API", description = "Update Comment Rest API is used to update comment in database using postId")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable long postId, @PathVariable long commentId,
			@Valid @RequestBody CommentDto commentDto) {

		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return ResponseEntity.ok(updatedComment);
	}

	@Operation(summary = "Delete Comment Rest API", description = "Delete Comment Rest API is used to delete comment from database using post id and comment id")
	@ApiResponse(responseCode = "200", description = "Http Status 200 Success")
	@SecurityRequirement(name = "Bear Authentication")
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return ResponseEntity.ok("Comment deleted successfully.");
	}
}
