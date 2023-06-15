package com.blog.restapi.payload.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentResponse {

	private long id;

	@NotEmpty(message = "Comment name can't be empty")
	private String name;

	@NotEmpty(message = "Comment email can't be empty")
	@Email
	private String email;

	@NotEmpty(message = "Comment body can't be empty")
	@Size(min = 10, message = "Comment body must have at least 10 characters")
	private String body;
}
