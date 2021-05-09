package com.mshindelar.lockegameservice.repository;

import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SquadlockeRepository extends MongoRepository<Squadlocke, String> {
    Optional<Squadlocke> findById(String id);
}
