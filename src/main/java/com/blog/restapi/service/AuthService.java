package com.blog.restapi.service;

import com.blog.restapi.payload.LoginDto;
import com.blog.restapi.payload.RegisterDto;

public interface AuthService {

	String login(LoginDto loginDto);

	String register(RegisterDto registerDto);
}
