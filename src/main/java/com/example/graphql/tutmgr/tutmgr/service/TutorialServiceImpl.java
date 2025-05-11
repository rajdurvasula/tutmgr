package com.example.graphql.tutmgr.tutmgr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.graphql.tutmgr.tutmgr.data.AuthorEntity;
import com.example.graphql.tutmgr.tutmgr.data.TutorialEntity;
import com.example.graphql.tutmgr.tutmgr.model.Author;
import com.example.graphql.tutmgr.tutmgr.model.Tutorial;
import com.example.graphql.tutmgr.tutmgr.repo.AuthorRepo;
import com.example.graphql.tutmgr.tutmgr.repo.TutorialRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TutorialServiceImpl implements TutorialService {

    private static final Logger logger = LoggerFactory.getLogger(TutorialServiceImpl.class);
    
    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private TutorialRepo tutorialRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<Tutorial> findAll() {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();
        List<TutorialEntity> tutorialEntities = tutorialRepo.findAll();
        for (TutorialEntity tutorialEntity : tutorialEntities) {
            tutorials.add(convertToTutorial(tutorialEntity));
        }
        return tutorials;
        //throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Tutorial findById(Long id) {
        Optional<TutorialEntity> optionalTutorialEntity = tutorialRepo.findById(id);
        if (optionalTutorialEntity.isPresent()) {
            return convertToTutorial(optionalTutorialEntity.get());
        } else {
            throw new RuntimeException("Tutorial with Id: "+id+" not found");
        }
        
        //throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Tutorial> findByAuthorId(Long authorId) {
        logger.info("Finding Tutorials by Author: {}", authorId);
        Optional<List<TutorialEntity>> optionalTutorialEntities = tutorialRepo.findByAuthorId(authorId);
        if (optionalTutorialEntities.isPresent()) {
            List<TutorialEntity> tutorialEntities = optionalTutorialEntities.get();
            List<Tutorial> tutorials = tutorialEntities.stream().map(tutorialEntity -> convertToTutorial(tutorialEntity)).toList();
            return tutorials;
        } else {
            throw new RuntimeException("No tutorials found for Author with Id: "+authorId);
        }
        //throw new UnsupportedOperationException("Unimplemented method 'findByAuthorId'");
    }

    @Override
    public Tutorial save(Tutorial tutorial) {
        Author author = tutorial.getAuthor();
        AuthorEntity authorEntity = authorRepo.findById(author.getId()).get();
        TutorialEntity tutorialEntity = objectMapper.convertValue(tutorial, TutorialEntity.class);
        tutorialEntity.setAuthorEntity(authorEntity);
        TutorialEntity newTutorialEntity = tutorialRepo.save(tutorialEntity);
        return convertToTutorial(newTutorialEntity);
        //throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public String delete(Long id) {
        try {
            tutorialRepo.deleteById(id);
            return "Tutorial with Id: "+id+" deleted.";
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete Tutorial with Id: "+id);
        }
        //throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Tutorial update(Long id, Tutorial tutorial) {
        Optional<TutorialEntity> optionalTutorialEntity = tutorialRepo.findById(id);
        if (optionalTutorialEntity.isPresent()) {
            TutorialEntity tutorialEntity = optionalTutorialEntity.get();
            if (tutorial.getName() != null) {
                tutorialEntity.setName(tutorial.getName());
            }
            if (tutorial.getDescription() != null) {
                tutorialEntity.setDescription(tutorial.getDescription());
            }
            TutorialEntity updatedTutorialEntity = tutorialRepo.save(tutorialEntity);
            return convertToTutorial(updatedTutorialEntity);
        } else {
            throw new RuntimeException("Tutorial with Id: "+id+" not found.");
        }
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    private Tutorial convertToTutorial(TutorialEntity tutorialEntity) {
        AuthorEntity authorEntity = tutorialEntity.getAuthorEntity();
        Author author = convertToAuthor(authorEntity);
        return new Tutorial(tutorialEntity.getId(), tutorialEntity.getName(), tutorialEntity.getDescription(), author);
    }

    private Author convertToAuthor(AuthorEntity authorEntity) {
        return new Author(authorEntity.getId(), authorEntity.getFirstName(), authorEntity.getLastName());
    }


}
