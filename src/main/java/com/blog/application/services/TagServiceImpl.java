package com.blog.application.services;

import com.blog.application.models.Tag;
import com.blog.application.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Tag findTagsByName(String name) {
        List<Tag> tagsList = tagRepository.findAll();
        Tag tags = new Tag();
        for(Tag tag : tagsList){
            if(tag.getName().equalsIgnoreCase(name)){
                tags = tag;
            }
        }
        return tags;
    }

    @Override
    public boolean checkTagByName(String tagName) {
        List<Tag> tagsList = tagRepository.findAll();
        for(Tag tags : tagsList){
            if(tags.getName().equalsIgnoreCase(tagName)){
                return true;
            }
        }
        return false;
    }
}
