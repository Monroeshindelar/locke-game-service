package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.configuration.LockeGameServiceConfiguration;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.generic.challonge.Tournament;
import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;
import com.mshindelar.lockegameservice.entity.squadlocke.*;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.TournamentSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.state.*;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import com.mshindelar.lockegameservice.repository.SquadlockeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    private static Logger logger = LoggerFactory.getLogger(SquadlockeController.class);

    @PutMapping("create")
    private Squadlocke create(@RequestParam(name = "participantId") String participantId, @RequestBody SquadlockeSettings squadlockeSettings) {
        logger.info("Starting squadlocke game creation for " + participantId);
        SquadlockeParticipant creator = new SquadlockeParticipant(participantId);

        Set<SquadlockeParticipant> participants = new HashSet<>();
        participants.add(creator);

        GameGeneration gameGeneration = gameGenerationRepository.findByGenerationId(squadlockeSettings.getGenerationId()).orElseThrow(() -> {
            logger.error("Generation with id " + squadlockeSettings.getGenerationId() + " could not be located.");
            return new RuntimeException("Invalid Generation ID: " + squadlockeSettings.getGenerationId());
        });

        Squadlocke squadlocke = new Squadlocke();
        squadlocke.setCreator(creator);
        squadlocke.setSettings(squadlockeSettings);
        squadlocke.setParticipants(participants);

        squadlocke.setGameState(new RegistrationGameState());
        squadlocke.setCreatedAt(new Date());
        squadlocke = squadlockeRepository.save(squadlocke);

        logger.info("Squadlocke " + squadlocke.getId() + " created.");
        return squadlocke;
    }

    @GetMapping("{id}")
    private Squadlocke get(@PathVariable("id") String id) {
        return squadlockeRepository.findById(id).orElse(null);
    }

    @PostMapping("{gameId}/join")
    private Squadlocke join(@PathVariable(name = "gameId") String gameId, @RequestParam(name = "participantId") String participantId) {
        logger.info("Player " + participantId + " attempting to register for game " + gameId);
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        if(squadlocke == null) {
            logger.error("Squadlocke with id " + gameId + " does not exist.");
            return null;
        }

        //Players should only be able to join a game during the registration phase
        if(squadlocke.getGameState().getGameStateType() != GameStateType.REGISTRATION) {
            logger.error("Cannot attempt to join a squadlocke that is not in the registration state");
            return null;
        }

        /* TODO:
         * If the game is invite only, check if the registered player is in the invite list.
         */

        SquadlockeParticipant squadlockeParticipant = new SquadlockeParticipant(participantId);
        squadlocke.addParticipant(squadlockeParticipant);

        squadlockeRepository.save(squadlocke);
        logger.info("Player " + participantId + " registered to game " + participantId);

        return squadlocke;
    }

    @PostMapping("{gameId}/start")
    private Squadlocke start(@PathVariable("gameId") String gameId) {
        Squadlocke squadlocke = squadlockeRepository.findById(gameId).orElse(null);

        if(squadlocke == null) {
            logger.error("Squadlocke " + gameId + " does not exist.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game " + gameId + " does not exist.");
        }
        //A game that is not in the registration state cannot be started
        if(squadlocke.getGameState().getGameStateType() != GameStateType.REGISTRATION) {
            logger.error("Game " + gameId + " is not in the registration state.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Specified game is not in the registration state");
        }

        squadlocke.setGameState(new CheckpointGameState(1));

        squadlockeRepository.save(squadlocke);

        logger.info("Started squadlocke " + gameId);

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
        if(squadlocke.getGameState().getGameStateType() != GameStateType.CHECKPOINT) return null;

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        if(participant == null) return null;
        participant.readyUp();

        if(squadlocke.allPlayersReady()) {
            TournamentSettings tournamentSettings = squadlocke.getSettings().getTournamentSettings();
            String uri = lockeGameServiceConfiguration.getTournament().getUri() + "tournaments.json?api_key=" +
                    lockeGameServiceConfiguration.getTournament().getKey();

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.setAccept(Collections.singletonList(MediaType.ALL));
            tournamentSettings.setName("gameservicetest");


            tournamentSettings.setName("stringtesttournamentsettings");
            HttpEntity<TournamentSettings> request = new HttpEntity<>(tournamentSettings);


            ResponseEntity<Tournament> response = restTemplate.exchange(uri, HttpMethod.POST, request, Tournament.class);

            Tournament resp = response.getBody();
        }

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
