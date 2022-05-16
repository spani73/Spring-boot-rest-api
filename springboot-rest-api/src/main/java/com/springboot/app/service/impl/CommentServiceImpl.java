package com.springboot.app.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.app.entity.Comment;
import com.springboot.app.entity.Post;
import com.springboot.app.exception.AppApiException;
import com.springboot.app.exception.ResourceNotFoundException;
import com.springboot.app.payload.CommentDto;
import com.springboot.app.repository.CommentRepository;
import com.springboot.app.repository.PostRepository;
import com.springboot.app.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}



	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//set post too comment entity
		comment.setPost(post);
		
		//save comment to db
		Comment newComment = commentRepository.save(comment);
		return mapToDto(newComment);
	}
	
	private CommentDto mapToDto(Comment comment) {
		
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());
		return commentDto;
		
	}
	
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = mapper.map(commentDto, Comment.class);
		
//		Comment comment = new Comment();
//		comment.setId(commentDto.getId());
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
		return comment;
		
	}



	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// TODO Auto-generated method stub
		//retrieve comments by post id
		
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		//list of comments to list of comment dtos.
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}



	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		//retrieve post by id 
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//retrve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id",commentId));
		
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		return mapToDto(comment);
	}



	@Override
	public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
		// TODO Auto-generated method stub
		//retrieve a post
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());
		
		Comment updatedcomment = commentRepository.save(comment);
		return mapToDto(updatedcomment);
	}



	@Override
	public void deleteComment(Long postId, Long commentId) {
		// TODO Auto-generated method stub
		//retrieve a post
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		}
		
		commentRepository.delete(comment);
		
	}
	
}
