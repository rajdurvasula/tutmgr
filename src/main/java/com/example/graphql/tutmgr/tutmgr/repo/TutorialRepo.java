package com.example.graphql.tutmgr.tutmgr.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.graphql.tutmgr.tutmgr.data.TutorialEntity;

@Repository
public interface TutorialRepo extends JpaRepository<TutorialEntity, Long> {
    
    @Query(value = "SELECT * FROM Tutorial t WHERE t.author_id = :authorId", nativeQuery = true)
    Optional<List<TutorialEntity>> findByAuthorId(@Param("authorId") Long authorId);
}
