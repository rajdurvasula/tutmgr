package com.example.graphql.tutmgr.tutmgr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.graphql.tutmgr.tutmgr.data.AuthorEntity;

@Repository
public interface AuthorRepo extends JpaRepository<AuthorEntity, Long> {
    
}
