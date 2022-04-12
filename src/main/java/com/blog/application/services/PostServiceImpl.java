package com.blog.application.services;

import com.blog.application.models.Comment;
import com.blog.application.models.Post;
import com.blog.application.models.Tag;
import com.blog.application.repository.PostRepository;
import com.blog.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Post posts) {
        postRepository.save(posts);
        return posts;
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> optional = postRepository.findById(id);
        Post posts = null;
        if(optional.isPresent()){
            posts = optional.get();
        }else{
            throw new RuntimeException("Post not found for id :: " + id);
        }
        return posts;
    }

    @Override
    public void deletePostById(long id) {
        Post entity = postRepository.getById(id);
        postRepository.delete(entity);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize, String sortPost, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPost).ascending() :
                Sort.by(sortPost).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public List<Post> findByKeyword(String keyword) {
        return postRepository.findByKeyword(keyword);
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> list = (List<Post>) postRepository.findAll();
        return list;
    }

    @Override
    @Transactional
    public void saveComment(Long id, Comment comments) {
        Optional<Post> optional = postRepository.findById(id);
        Post posts=optional.get();
        posts.addComments(comments);
        System.out.println(id);
        this.postRepository.save(posts);
    }


    @Override
    public List<Post> filterResult(List<String> filter) {
        List<Post> posts = new ArrayList<>();
        for(String keyword : filter){
            posts.addAll(postRepository.findByKeyword(keyword));
        }
        return posts;
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> filterByAuthor(String[] authors) {
        List<Post> posts = new ArrayList<>();
        for(String author : authors){
            posts.addAll(postRepository.findByAuthor(author));
        }
        return posts;
    }
}
