package com.mshindelar.lockegameservice.service;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.*;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeParticipant;
import com.mshindelar.lockegameservice.pokeapi.PokeApiClient;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import com.mshindelar.lockegameservice.pokeapi.model.PokemonSpecies;
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

    @Autowired
    private PokeApiClient pokeApiClient;

    public Encounter getEncounter(SquadlockeParticipant participant, String generationId, String locationId, List<EncounterMode> modes,
                                  EncounterGeneratorSettings settings, boolean filterSpeciesClause) {
        List<Encounter> encounters = this.encounterRepository.findEncountersForLocationByMode(generationId, participant.getGameId(), locationId, modes);

        if(filterSpeciesClause) {
            encounters = encounters.stream()
                    .filter(e -> {
                        Pokemon pokemon = this.pokeApiClient.getPokemon(e.getNationalDexNumber());
                        PokemonSpecies species = this.pokeApiClient.getPokemonSpecies(pokemon.getSpeciesId());
                        return !participant.getBox().containsSpecies(species.getEvolutionChainId());
                    })
                    .collect(Collectors.toList());
        }

        EncounterGenerator encounterGenerator = this.encounterGeneratorFactory.getEncounterGenerator(settings.getEncounterProbability());
        return encounterGenerator.getEncounter(encounters);
    }

    public List<Encounter> getAllEncountersForLocation(String generationId, String locationId, int gameId) {
        return this.encounterRepository.findAllEncountersForLocation(generationId, locationId, gameId);
    }

    public List<EncounterMode> getAllEncounterModesForLocation(String generationId, int gameId, String locationId) {
        return this.encounterRepository.findAllEncountersForGameAndLocation(generationId, gameId, locationId).stream()
                .map(Encounter::getEncounterMode)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllEncounterLocationsForGeneration(String generationId, int gameId) {
        return this.encounterRepository.findAllEncountersForGame(generationId, gameId).stream()
                .map(Encounter::getLocationId)
                .distinct()
                .collect(Collectors.toList());
    }
}
