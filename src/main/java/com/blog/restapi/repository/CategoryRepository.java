package com.blog.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.restapi.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
