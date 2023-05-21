package com.blog.restapi.service;

import java.util.List;

import com.blog.restapi.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);

	List<CommentDto> getCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId, long commentId);
	
	CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
	
	void deleteCommentById(long postId, long commentId);
}
