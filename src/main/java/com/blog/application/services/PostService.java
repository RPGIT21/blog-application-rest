package com.blog.application.services;

import com.blog.application.models.Comment;
import com.blog.application.models.Post;
import com.blog.application.models.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    public List<Post> findAll();
    Post savePost(Post posts);
    Post getPostById(long id);
    void deletePostById(long id);
    Page<Post> findPaginated(int pageNo, int pageSize, String sortPost, String sortOrder);

    List<Post> findByKeyword(String keyword);

    List<Post> getAllPosts();

    void saveComment(Long id, Comment comments);

    //boolean checkTagByName(String tagName);

    //Tag findTagByName(String tagName);

    List<Post> filterResult(List<String> filter);

    List<Post> findAllPosts();

    List<Post> filterByAuthor(String[] author);
}
