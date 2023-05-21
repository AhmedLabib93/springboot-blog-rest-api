package com.blog.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.restapi.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findPostByCategoryId(Long id);
}
