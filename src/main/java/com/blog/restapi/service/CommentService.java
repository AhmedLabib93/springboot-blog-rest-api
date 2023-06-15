package com.blog.restapi.service;

import java.util.List;

import com.blog.restapi.payload.response.CommentResponse;

public interface CommentService {

	CommentResponse createComment(long postId, CommentResponse commentDto);

	List<CommentResponse> getCommentsByPostId(long postId);
	
	CommentResponse getCommentById(long postId, long commentId);
	
	CommentResponse updateComment(long postId, long commentId, CommentResponse commentDto);
	
	void deleteCommentById(long postId, long commentId);
}
