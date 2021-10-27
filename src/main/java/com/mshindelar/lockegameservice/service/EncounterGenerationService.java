package com.mshindelar.lockegameservice.service;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.*;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeParticipant;
import com.mshindelar.lockegameservice.repository.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EncounterGenerationService {

    @Autowired
    private EncounterRepository encounterRepository;

    @Autowired
    private EncounterGeneratorFactory encounterGeneratorFactory;

    public Encounter getEncounter(SquadlockeParticipant participant, String generationId, String locationId, EncounterMode mode,
                                  EncounterGeneratorSettings settings, boolean filterSpeciesClause) {
        List<Encounter> encounters = this.encounterRepository.findEncountersForLocationByMode(generationId, participant.getGameId(), locationId, mode);

        if(filterSpeciesClause) {
            encounters = encounters.stream()
                    .filter(e -> !participant.getBox().containsSpecies(e.getNationalDexNumber()))
                    .collect(Collectors.toList());
        }

        EncounterGenerator encounterGenerator = this.encounterGeneratorFactory.getEncounterGenerator(settings.getEncounterProbability());
        return encounterGenerator.getEncounter(encounters);
    }
}
