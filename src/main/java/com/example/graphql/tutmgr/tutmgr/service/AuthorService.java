package com.example.graphql.tutmgr.tutmgr.service;

import java.util.List;

import com.example.graphql.tutmgr.tutmgr.model.Author;

public interface AuthorService {

    List<Author> findAll();
    Author findById(Long id);
    Author save(Author author);
    String delete(Long id);
    Author update(Long id, Author author);
}
