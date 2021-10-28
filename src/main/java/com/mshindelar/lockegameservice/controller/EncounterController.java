package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.Encounter;
import com.mshindelar.lockegameservice.service.EncounterGenerationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encounters")
@Slf4j
public class EncounterController {

    @Autowired
    private EncounterGenerationService encounterGenerationService;

    @GetMapping("{generationId}/{locationId}")
    List<Encounter> getAllEncountersForLocation(@PathVariable("generationId") String generationId, @PathVariable("locationId") String locationId) {
        return this.encounterGenerationService.getAllEncountersForLocation(generationId, locationId);
    }

}
