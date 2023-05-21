package com.blog.restapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blog.restapi.entity.Comment;
import com.blog.restapi.entity.Post;
import com.blog.restapi.exception.BlogAPIException;
import com.blog.restapi.exception.ResourceNotFoundException;
import com.blog.restapi.payload.CommentDto;
import com.blog.restapi.repository.CommentRepository;
import com.blog.restapi.repository.PostRepository;
import com.blog.restapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	CommentRepository commentRepository;
	PostRepository postRepository;
	ModelMapper modelMapper;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	private CommentDto mapToDto(Comment comment) {
		/*
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		*/
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		/*
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		*/
		Comment comment = modelMapper.map(commentDto, Comment.class);
		return comment;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		return mapToDto(newComment);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = commentRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		if(!post.getId().equals(comment.getPost().getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		return mapToDto(comment);
	}
	
	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		if(!post.getId().equals(comment.getPost().getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return mapToDto(commentRepository.save(comment));
		
	}
	
	@Override
	public void deleteCommentById(long postId, long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Id", postId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "Id", commentId));
		if(!post.getId().equals(comment.getPost().getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		commentRepository.deleteById(commentId);
	}
}
