package com.mshindelar.lockegameservice.repository;

import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameGenerationRepository extends MongoRepository<GameGeneration, String> {
    Optional<GameGeneration> findByGenerationId(int generationId);
}
