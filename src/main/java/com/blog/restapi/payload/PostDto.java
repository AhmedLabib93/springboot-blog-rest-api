package com.blog.restapi.payload;

import java.util.Set;

import com.blog.restapi.entity.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto {

	private Long id;

	@Schema(description = "Post title")
	@NotEmpty(message = "Post title can't be empty")
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;

	@Schema(description = "Post description ")
	@NotEmpty(message = "Post description can't be empty")
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;

	@Schema(description = "Post content")
	@NotEmpty(message = "Post content can't be empty")
	private String content;
	private Set<CommentDto> comments;

	@Schema(description = "Post's Category id")
	private Long categoryId;
}
