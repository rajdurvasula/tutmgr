package com.example.graphql.tutmgr.tutmgr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.graphql.tutmgr.tutmgr.model.Author;
import com.example.graphql.tutmgr.tutmgr.model.Tutorial;
import com.example.graphql.tutmgr.tutmgr.service.AuthorServiceImpl;
import com.example.graphql.tutmgr.tutmgr.service.TutorialServiceImpl;

@Controller
public class TutorialController {
    
    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private TutorialServiceImpl tutorialService;

    @QueryMapping
    public List<Author> findAllAuthors() {
        return authorService.findAll();
    }

    @MutationMapping
    public Author createAuthor(@Argument String firstName, @Argument String lastName) {
        Author author = new Author(firstName, lastName);
        return authorService.save(author);
    }

    @QueryMapping
    public List<Tutorial> findAllTutorials() {
        return tutorialService.findAll();
    }

    @QueryMapping
    public List<Tutorial> findTutorialsByAuthor(@Argument Long authorId) {
        return tutorialService.findByAuthorId(authorId);
    }

    @MutationMapping
    public Tutorial createTutorial(@Argument String name, @Argument String description, @Argument Long author) {
        Author matchedAuthor = authorService.findById(author);
        Tutorial tutorial = new Tutorial(name, description, matchedAuthor);
        return tutorialService.save(tutorial);
    }
}
