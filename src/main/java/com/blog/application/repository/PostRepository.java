package com.blog.application.repository;

import com.blog.application.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select * from posts p where p.title like %?1% or p.content like %?1%"
            , nativeQuery = true)
    List<Post> findByKeyword(String keyword);

    @Query(value = "select * from posts p where p.author like %:author%", nativeQuery = true)
    List<Post> findByAuthor(@Param("author") String author);
}
