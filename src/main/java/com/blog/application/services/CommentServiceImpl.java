package com.blog.application.services;

import com.blog.application.models.Comment;
import com.blog.application.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(long id, Comment comments){
        this.commentRepository.save(comments);
    }

    @Override
    public List<Comment> listOfComments() {
        return this.commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(long id) {
        Optional<Comment> optional = commentRepository.findById(id);
        Comment comments = null;
        if(optional.isPresent()){
            comments = optional.get();
        }else{
            throw new RuntimeException("Post not found for id :: " + id);
        }
        return comments;
    }

    @Override
    public void deleteCommentById(long id) {
        this.commentRepository.deleteById(id);
    }
}
