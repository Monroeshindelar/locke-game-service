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

    /**
     * Gets all games a user is a participant of
     */
    @Query("{ 'participants._id': ?0 }")
    List<Squadlocke> findByParticipantId(String participantId);

    /**
     * Gets all games that have open accessibility, are in the registration phase, and
     * that the current searcher is not a member of
     */
    @Query("{ 'settings.accessibility': 'OPEN', 'gameState.gameStateType': 'REGISTRATION', 'participants._id': { $nin: [?0]} }")
    List<Squadlocke> findJoinableGames(String userId);
}
