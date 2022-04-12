package com.blog.application.controllers;

import com.blog.application.models.Post;
import com.blog.application.models.Tag;
import com.blog.application.repository.PostRepository;
import com.blog.application.services.PostService;
import com.blog.application.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @GetMapping("/posts")
    public List<Post> showAllPosts(){
        return postRepository.findAll();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable long id){
        try{
            this.postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/posts")
    public Post savePost(@RequestBody Post post){
        post.setPublishedAt(LocalDate.now());
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());
        post.setExcerpt(post.getContent().substring(0,10));

        return postRepository.save(post);
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@RequestBody Post post){
        post.setPublishedAt(LocalDate.now());
        post.setCreatedAt(LocalDate.now());
        post.setUpdatedAt(LocalDate.now());
        post.setExcerpt(post.getContent().substring(0,10));

        return postRepository.save(post);
    }

    @PostMapping("/posts/search/{keyword}")
    public List<Post> search(@PathVariable String keyword){
        List<Post> postsList;
        postsList = keyword == null? postRepository.findAll(): postRepository.findByKeyword(keyword);
        return postsList;
    }

    @GetMapping("/posts/page")
    Page<Post> getPosts(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<String> sortBy
    ){
        return postRepository.findAll(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC, sortBy.orElse("publishedAt")
                )
        );
    }

    @PostMapping("/filterByTag")
    public List<Post> filterByTag(@RequestParam("tag") String tags){
        Tag tag = tagService.findTagsByName(tags);
        List<Post> posts = tag.getPosts();
        return posts;
    }

    @PostMapping("/filterByAuthor")
    public List<Post> filterByAuthor(@RequestParam("author") String[] author){
        List<Post> posts = author == null ? postService.findAllPosts() : postService.filterByAuthor(author);

        return posts;
    }
}
