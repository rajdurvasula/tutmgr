package com.example.graphql.tutmgr.tutmgr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tutorial {
    
    private Long id;
    private String name;
    private String description;
    private Author author;

    public Tutorial(String name, String description, Author author) {
        this.name = name;
        this.description = description;
        this.author = author;
    }
}
