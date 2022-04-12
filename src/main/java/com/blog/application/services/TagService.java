package com.blog.application.services;

import com.blog.application.models.Tag;

public interface TagService {
    Tag findTagsByName(String tags);
    boolean checkTagByName(String tagName);
}
