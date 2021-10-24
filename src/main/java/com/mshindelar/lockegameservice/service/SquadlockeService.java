package com.mshindelar.lockegameservice.service;

import com.mshindelar.lockegameservice.configuration.LockeGameServiceConfiguration;
import com.mshindelar.lockegameservice.controller.SquadlockeController;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.generic.challonge.Tournament;
import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;
import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeParticipant;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.TournamentSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.state.CheckpointGameState;
import com.mshindelar.lockegameservice.entity.squadlocke.state.GameStateType;
import com.mshindelar.lockegameservice.entity.squadlocke.state.RegistrationGameState;
import com.mshindelar.lockegameservice.exception.DuplicateGameResourceException;
import com.mshindelar.lockegameservice.exception.GameResourceNotFoundException;
import com.mshindelar.lockegameservice.exception.ImproperGameStateException;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import com.mshindelar.lockegameservice.repository.SquadlockeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SquadlockeService {

    @Autowired
    private SquadlockeRepository squadlockeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LockeGameServiceConfiguration gameServiceConfiguration;

    @Autowired
    private GameGenerationRepository gameGenerationRepository;

    private static Logger logger = LoggerFactory.getLogger(SquadlockeController.class);

    public Squadlocke createSquadlocke(String participantId, SquadlockeSettings squadlockeSettings) {
        logger.info("Starting squadlocke game creation for " + participantId);
        SquadlockeParticipant creator = new SquadlockeParticipant(participantId);

        Set<SquadlockeParticipant> participants = new HashSet<>();
        participants.add(creator);

        GameGeneration gameGeneration = gameGenerationRepository.findByGenerationId(squadlockeSettings.getGenerationId()).orElseThrow(() -> {
            logger.error("Generation with id " + squadlockeSettings.getGenerationId() + " could not be located.");
            return new RuntimeException("Invalid Generation ID: " + squadlockeSettings.getGenerationId());
        });

        Squadlocke squadlocke = new Squadlocke();
        squadlocke.setCreatorId(participantId);
        squadlocke.setSettings(squadlockeSettings);
        squadlocke.setParticipants(participants);

        squadlocke.setGameState(new RegistrationGameState());
        squadlocke.setCreatedAt(new Date());
        squadlocke = squadlockeRepository.save(squadlocke);

        logger.info("Squadlocke " + squadlocke.getId() + " created.");
        return squadlocke;
    }

    public Squadlocke getSquadlocke(String gameId) {
        return squadlockeRepository.findById(gameId).orElseThrow(() -> {
            logger.error("No game with id: " + gameId);
           return new GameResourceNotFoundException("Game with id " + gameId + " cannot be found.");
        });
    }

    public Squadlocke joinSquadlocke(String gameId, String participantId) {
        logger.info("Player " + participantId + " attempting to register for game " + gameId);
        Squadlocke squadlocke = this.getSquadlocke(gameId);
        SquadlockeParticipant squadlockeParticipant;
        try {
            squadlocke.getParticipantById(participantId);
            throw new DuplicateGameResourceException("User is already a participant.");
        } catch(GameResourceNotFoundException ignored) { }

        //Players should only be able to join a game during the registration phase
        if(squadlocke.getGameState().getGameStateType() != GameStateType.REGISTRATION) {
            logger.error("Cannot attempt to join a squadlocke that is not in the registration state");
            throw new ImproperGameStateException("Specified game is no longer joinable.");
        }

        /* TODO:
         * If the game is invite only, check if the registered player is in the invite list.
         */

        squadlockeParticipant = new SquadlockeParticipant(participantId);
        squadlocke.addParticipant(squadlockeParticipant);

        squadlockeRepository.save(squadlocke);
        logger.info("Player " + participantId + " registered to game " + participantId);

        return squadlocke;
    }

    public Squadlocke startSquadlocke(String gameId) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        //A game that is not in the registration state cannot be started
        if(squadlocke.getGameState().getGameStateType() != GameStateType.REGISTRATION) {
            logger.error("Cannot attempt to start a squadlocke that is not in the registration state");
            throw new ImproperGameStateException("Specified game is not startable.");
        }

        squadlocke.setGameState(new CheckpointGameState(1));

        squadlockeRepository.save(squadlocke);

        logger.info("Started squadlocke " + gameId);

        return squadlocke;
    }

    public Squadlocke finalizeSquadlocke(String gameId) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        //squadlocke.setGameState(GameStateType.FINALIZED);
        squadlockeRepository.save(squadlocke);
        return squadlocke;
    }

    public SquadlockeParticipant getParticipant(String gameId, String participantId) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);
        return squadlocke.getParticipantById(participantId);
    }

    public SquadlockeParticipant readyParticipant(String gameId, String participantId) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        if(squadlocke.getGameState().getGameStateType() != GameStateType.CHECKPOINT) {
            logger.error("Cannot ready up player outside of the checkpoint gamestate.");
            throw new ImproperGameStateException("Players cannot ready up outside of the checkpoint gamestate.");
        }

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        participant.readyUp();

        if(squadlocke.allPlayersReady()) {
            //TODO: Start tournament
            //TournamentSettings tournamentSettings = new TournamentSettings();
            //tournamentSettings.setName("LW-20 test tournament");

            String uri = this.gameServiceConfiguration.getTournament().getUri() + "tournaments.json?api_key=" +
                    this.gameServiceConfiguration.getTournament().getKey() + "&tournament[name]=gameServiceTestTwo";

            Tournament t = restTemplate.postForObject(uri, null, Tournament.class);

            int a = 1;
        }

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public SquadlockeParticipant addPokemonToTeam(String gameId, String participantId, Pokemon pokemon) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        participant.getTeam().addToTeam(pokemon);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public SquadlockeParticipant removePokemonFromTeam(String gameId, String participantId, int nationalDexNumber) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        Pokemon mask = new Pokemon();
        mask.setNationalDexNumber(nationalDexNumber);

        participant.getTeam().removeFromTeam(mask);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public SquadlockeParticipant addPokemonToSideboard(String gameId, String participantId, Pokemon pokemon) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        participant.getTeam().addToSideBoard(pokemon);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public SquadlockeParticipant removePokemonFromSideboard(String gameId, String participantId, int nationalDexNumber) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        Pokemon mask = new Pokemon();
        mask.setNationalDexNumber(nationalDexNumber);

        participant.getTeam().removeFromSideboard(mask);

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public SquadlockeParticipant setImmunitySlot(String gameId, String participantId, Pokemon pokemon) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);
        participant.getTeam().setImmunitySlot(pokemon);

        return participant;
    }

    public List<Squadlocke> getSquadlockeByUserId(String userId) {
        return this.squadlockeRepository.findByParticipantId(userId);
    }

    public List<Squadlocke> getJoinableGames() {
        return this.squadlockeRepository.findJoinableGames();
    }
}
