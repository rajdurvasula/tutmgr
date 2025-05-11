package com.example.graphql.tutmgr.tutmgr.service;

import java.util.List;

import com.example.graphql.tutmgr.tutmgr.model.Tutorial;

public interface TutorialService {
    
    List<Tutorial> findAll();
    Tutorial findById(Long id);
    List<Tutorial> findByAuthorId(Long authorId);
    Tutorial save(Tutorial tutorial);
    String delete(Long id);
    Tutorial update(Long id, Tutorial tutorial);
}
