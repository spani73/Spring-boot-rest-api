package com.springboot.app.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	@NotEmpty(message = "Name Should not be Empty")
	private String name;
	
	@NotEmpty(message = "Email should not be empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 10 , message = "should be atleast 10 characters")
	private String body;
}
