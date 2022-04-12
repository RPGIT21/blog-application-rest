package com.blog.application.controllers;

import com.blog.application.models.Comment;
import com.blog.application.repository.CommentRepository;
import com.blog.application.services.CommentService;
import com.blog.application.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/saveComment/{id}")
    public Comment saveComment(@PathVariable(value = "id") long id, @RequestBody Comment comments){
        comments.setCreatedAt(LocalDate.now());
        comments.setUpdatedAt(LocalDate.now());
        commentService.saveComment(id, comments);
        postService.saveComment(id, comments);
        return commentRepository.save(comments);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable long id){
        try{
            this.commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/saveComment/{id}")
    public Comment updateComment(@PathVariable long id, @RequestBody Comment comments){
        comments = commentService.getCommentById(id);
        postService.saveComment(id, comments);
        commentService.saveComment(id, comments);
        return commentRepository.save(comments);

    }
}
