package com.blog.restapi.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.restapi.entity.Role;
import com.blog.restapi.entity.User;
import com.blog.restapi.exception.BlogAPIException;
import com.blog.restapi.payload.LoginDto;
import com.blog.restapi.payload.RegisterDto;
import com.blog.restapi.repository.RoleRepository;
import com.blog.restapi.repository.UserRepository;
import com.blog.restapi.security.JwtTokenProvider;
import com.blog.restapi.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String login(LoginDto loginDto) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateJwtToken(authentication);

		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		if (userRepository.existsByEmail(registerDto.getEmail()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");
		if (userRepository.existsByUsername(registerDto.getUsername()))
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists!");

		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByName("USER").get());
		user.setRoles(roles);

		userRepository.save(user);

		return "User registered successfully!";
	}

}
