package com.mshindelar.lockegameservice.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshindelar.lockegameservice.configuration.LockeGameServiceConfiguration;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.generic.challonge.Tournament;
import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;
import com.mshindelar.lockegameservice.entity.squadlocke.*;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import com.mshindelar.lockegameservice.repository.SquadlockeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/games/squadlocke")
@Slf4j
public class SquadlockeController {

    @Autowired
    private SquadlockeRepository squadlockeRepository;

    @Autowired
    private GameGenerationRepository gameGenerationRepository;

    @Autowired
    private LockeGameServiceConfiguration lockeGameServiceConfiguration;

    @PutMapping("create")
    private Squadlocke create(@RequestParam(name = "participantId") String participantId, @RequestParam(name = "generationId") int generationId) throws Exception {
        SquadlockeParticipant creator = new SquadlockeParticipant(participantId);

        Set<SquadlockeParticipant> participants = new HashSet<>();
        participants.add(creator);

        GameGeneration gameGeneration = gameGenerationRepository.findByGenerationId(generationId).orElse(null);

        if(gameGeneration == null) return null;

        SquadlockeSettings squadlockeSettings = new SquadlockeSettings();
        squadlockeSettings.setGeneration(gameGeneration);

        Squadlocke squadlocke = Squadlocke.create(creator, squadlockeSettings);

        squadlocke.setParticipants(participants);
        //squadlocke.setGameState(GameStateType.REGISTRATION);

        //squadlockeRepository.save(squadlocke);

//        RestTemplate restTemplate = new RestTemplate();

//        String uri = lockeGameServiceConfiguration.getTournament().getUri() + "tournaments/q8ku4rrg.json?api_key=" + lockeGameServiceConfiguration.getTournament().getKey();

//        String response = restTemplate.getForObject(uri, String.class);

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        Tournament tournament = mapper.readValue(response, Tournament.class);

        return squadlocke;
    }

    @GetMapping("{id}")
    private Squadlocke get(@PathVariable("id") String id) {
        return squadlockeRepository.findById(id).orElse(null);
    }

    @PostMapping("{gameId}/join")
    private Squadlocke join(@PathVariable(name = "gameId") String gameId, @RequestParam(name = "participantId") String participantId) {
        //Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        Squadlocke squadlocke = squadlockeRepository.findById(participantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Game not found"));

        //Players should only be able to join a game during the registration phase
        //if(squadlocke.getGameState() != GameStateType.REGISTRATION) return null;

        SquadlockeParticipant squadlockeParticipant = new SquadlockeParticipant(participantId);
        squadlocke.addParticipant(squadlockeParticipant);

        squadlockeRepository.save(squadlocke);

        return squadlocke;
    }

    @PostMapping("{gameId}/start")
    private Squadlocke start(@PathVariable("gameId") String gameId) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        if(squadlocke == null) return null;
        //A game that is not in the registration state cannot be started
        //if(squadlocke.getGameState() != GameStateType.REGISTRATION) return null;

        //squadlocke.setGameState(GameStateType.CHECKPOINT);

        squadlockeRepository.save(squadlocke);

        return squadlocke;
    }

    @PostMapping("{gameId}/finalize")
    private Squadlocke finalize(@PathVariable("gameId") String gameId) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        //squadlocke.setGameState(GameStateType.FINALIZED);
        squadlockeRepository.save(squadlocke);
        return squadlocke;
    }

    @GetMapping("{gameId}/participants/{participantId}")
    private SquadlockeParticipant getParticipant(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        return squadlocke.getParticipantById(participantId);
    }

    @PostMapping("{gameId}/participants/{participantId}/ready")
    private Squadlocke ready(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        if(squadlocke == null) return null;
        //if(squadlocke.getGameState() != GameStateType.CHECKPOINT) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        if(participant == null) return null;
        participant.ready();

        squadlockeRepository.save(squadlocke);

        return squadlocke;
    }

    @PostMapping("{gameId}/participants/{participantId}/team/main/add")
    private SquadlockeParticipant addToTeam(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId, @RequestBody Pokemon pokemon) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        if(participant == null ) return null;

        participant.getTeam().addToTeam(pokemon);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    @PostMapping("{gameId}/participants/{participantId}/team/main/remove")
    private SquadlockeParticipant removeFromTeam(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                 @RequestParam(name = "nationalDexNumber") int nationalDexNumber) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        Pokemon mask = new Pokemon();
        mask.setNationalDexNumber(nationalDexNumber);

        participant.getTeam().removeFromTeam(mask);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    @PostMapping("{gameId}/participants/{participantId}/team/side/add")
    private SquadlockeParticipant addToSideBoard(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                 @RequestBody Pokemon pokemon) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        if(participant == null) return null;

        participant.getTeam().addToSideBoard(pokemon);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    @PostMapping("{gameId}/participants/{participantId}/team/side/remove")
    private SquadlockeParticipant removeFromSideBoard(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                      @RequestParam(name = "nationalDexNumber") int nationalDexNumber) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        if(participant == null) return null;

        Pokemon mask = new Pokemon();
        mask.setNationalDexNumber(nationalDexNumber);

        participant.getTeam().removeFromSideboard(mask);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    @PostMapping("{gameId}/participants/{participantId}/team/immunity")
    private SquadlockeParticipant setImmunitySlot(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                  @RequestBody Pokemon pokemon) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);
        if(squadlocke == null) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        participant.getTeam().setImmunitySlot(pokemon);

        return participant;
    }


}
