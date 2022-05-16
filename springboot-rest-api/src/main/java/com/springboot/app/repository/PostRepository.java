package com.springboot.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.entity.Post;


public interface PostRepository extends JpaRepository<Post,Long>{
	//No code required as jpa handles everything

}
