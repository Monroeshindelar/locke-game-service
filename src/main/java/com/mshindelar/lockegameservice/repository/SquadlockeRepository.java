package com.mshindelar.lockegameservice.repository;

import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SquadlockeRepository extends MongoRepository<Squadlocke, String> {
    Optional<Squadlocke> findById(String id);

    @Query("{ 'participants._id': ?0 }")
    List<Squadlocke> findByParticipantId(String participantId);
}
