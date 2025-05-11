package com.example.graphql.tutmgr.tutmgr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graphql.tutmgr.tutmgr.data.AuthorEntity;
import com.example.graphql.tutmgr.tutmgr.model.Author;
import com.example.graphql.tutmgr.tutmgr.repo.AuthorRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthorServiceImpl implements AuthorService {
    
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public List<Author> findAll() {
        List<AuthorEntity> authorEntities = authorRepo.findAll();
        List<Author> authors = authorEntities.stream().map(authorEntity -> objectMapper.convertValue(authorEntity, Author.class)).toList();
        return authors;
        //throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Author findById(Long id) {
        AuthorEntity authorEntity = authorRepo.findById(id).get();
        return objectMapper.convertValue(authorEntity, Author.class);
        //throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Author save(Author author) {
        AuthorEntity authorEntity = objectMapper.convertValue(author, AuthorEntity.class);
        AuthorEntity newAuthorEntity = authorRepo.save(authorEntity);
        return objectMapper.convertValue(newAuthorEntity, Author.class);
        //throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public String delete(Long id) {
        try {
            authorRepo.deleteById(id);
            return "Author with Id: "+id+" deleted.";
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete Author with Id: "+id);
        }
        //throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Author update(Long id, Author author) {
        Optional<AuthorEntity> optionalAuthorEntity = authorRepo.findById(id);
        if (optionalAuthorEntity.isPresent()) {
            AuthorEntity authorEntity = optionalAuthorEntity.get();
            if (author.getFirstName() != null) {
                authorEntity.setFirstName(author.getFirstName());
            }
            if (author.getLastName() != null) {
                authorEntity.setLastName(author.getLastName());
            }
            AuthorEntity updatedAuthorEntity = authorRepo.save(authorEntity);
            return objectMapper.convertValue(updatedAuthorEntity, Author.class);
        } else {
            throw new RuntimeException("Author with Id: "+id+" not found");
        }
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
