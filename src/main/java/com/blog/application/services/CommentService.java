package com.blog.application.services;

import com.blog.application.models.Comment;

import java.util.List;


public interface CommentService {

    void saveComment(long id, Comment comments);

    List<Comment> listOfComments();

    Comment getCommentById(long id);

    void deleteCommentById(long id);
}
