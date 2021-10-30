package com.mshindelar.lockegameservice.repository;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.Encounter;
import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterMode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EncounterRepository extends MongoRepository<Encounter, String> {

    @Query("{ generationId: ?0, locationId: ?1, gameId: ?2}")
    List<Encounter> findAllEncountersForLocation(String generationId, String locationId, int gameId);

    @Query("{ generationId: ?0, gameId: ?1, locationId: ?2 }")
    List<Encounter> findAllEncountersForGameAndLocation(String generationId, int gameId, String locationId);

    @Query("{ generationId: ?0, gameId: ?1, locationId: ?2, mode: ?3 }")
    List<Encounter> findEncountersForLocationByMode(String generationId, int gameId, String locationId, EncounterMode mode);
}
