package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.Encounter;
import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterMode;
import com.mshindelar.lockegameservice.pokeapi.PokeApiClient;
import com.mshindelar.lockegameservice.service.EncounterGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/encounters")
@Slf4j
public class EncounterController {

    @Autowired
    private EncounterGenerationService encounterGenerationService;

    @Autowired
    private PokeApiClient pokeApiClient;

    @GetMapping("{generationId}/{locationId}")
    List<Encounter> getAllEncountersForLocation(@PathVariable("generationId") String generationId, @PathVariable("locationId") String locationId,
                                                @RequestParam("gameId") int gameId) {
        return this.encounterGenerationService.getAllEncountersForLocation(generationId, locationId, gameId)
                .stream()
                .peek(e -> e.setModel(this.pokeApiClient.getPokemon(e.getNationalDexNumber())))
                .collect(Collectors.toList());
    }

    @GetMapping("{generationId}/{locationId}/modes/all")
    List<EncounterMode> getAllEncounterModesForLocation(@PathVariable("generationId") String generationId, @PathVariable("locationId") String locationId) {
        return this.encounterGenerationService.getAllEncounterModesForLocation(generationId, locationId);
    }

}
