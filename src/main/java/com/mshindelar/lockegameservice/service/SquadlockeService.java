package com.mshindelar.lockegameservice.service;

import com.mshindelar.lockegameservice.configuration.LockeGameServiceConfiguration;
import com.mshindelar.lockegameservice.controller.SquadlockeController;
import com.mshindelar.lockegameservice.entity.EncounterGenerator.Encounter;
import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterMode;
import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.squadlocke.Nature;
import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeParticipant;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockePokemon;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.state.CheckpointGameState;
import com.mshindelar.lockegameservice.entity.squadlocke.state.GameStateType;
import com.mshindelar.lockegameservice.entity.squadlocke.state.RegistrationGameState;
import com.mshindelar.lockegameservice.exception.DuplicateGameResourceException;
import com.mshindelar.lockegameservice.exception.GameResourceNotFoundException;
import com.mshindelar.lockegameservice.exception.ImproperGameStateException;
import com.mshindelar.lockegameservice.pokeapi.PokeApiClient;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import com.mshindelar.lockegameservice.repository.GameGenerationRepository;
import com.mshindelar.lockegameservice.repository.SquadlockeRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private GameGenerationRepository gameGenerationRepository;

    @Autowired
    private EncounterGenerationService encounterGenerationService;

    @Autowired
    private PokeApiClient pokeApiClient;

    @Autowired
    private LockeGameServiceConfiguration gameServiceConfiguration;

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

//            String uri = this.gameServiceConfiguration.getTournament().getUri() + "tournaments.json?api_key=" +
//                    this.gameServiceConfiguration.getTournament().getKey() + "&tournament[name]=gameServiceTestTwo";

//            Tournament t = restTemplate.postForObject(uri, null, Tournament.class);

            int a = 1;
        }

        squadlockeRepository.save(squadlocke);

        return participant;
    }

    public List<Squadlocke> getSquadlockeByUserId(String userId) {
        return this.squadlockeRepository.findByParticipantId(userId);
    }

    public List<Squadlocke> getJoinableGames(String userId) {
        return this.squadlockeRepository.findJoinableGames(userId);
    }

    public Encounter getEncounter(String gameId, String participantId, String locationId, EncounterMode encounterMode,
                                  boolean filterSpeciesClause) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        // If the location id exists in the box already, there are two options
        // 1. The user is trying to get an encounter for a route in which they
        //    have already encountered a pokemon. Not valid.
        // 2. The user did not specify that they would like to filter pokemon
        //    that they already have from the results and they encountered a
        //    pokemon that meets the species clause requirements. Valid.
        if(participant.getBox().containsEncounterForLocation(locationId)) {
            SquadlockePokemon p = participant.getBox().getEncounterForLocation(locationId);

            // This is an unfinished dummy encounter
            if(p.getNickname() == null) {
                // If this isn't a species clause reroll
                if(!participant.getBox().containsSpecies(p)) {
                    // Nickname is an indicator that the pokemon is "encountered"
                    // Set it so that we know it's no longer a placeholder for an
                    // unfinished encounter
                    p.setNickname(p.getModel().getName());
                    return null;
                } else {
                    // The dummy was subject to the species clause
                    // Remove it so we don't have multiple encounters
                    // with the same location id floating around in the
                    // box
                    participant.getBox().remove(p);
                }
            } else {
                // Pokemon with location id exists in the box and has been
                // nicknamed. This is just an attempt to get another encounter,
                // shame!
                return null;
            }
        }

        Encounter encounter = this.encounterGenerationService.getEncounter(participant, "" + squadlocke.getSettings().getGenerationId(),
                locationId, encounterMode, squadlocke.getSettings().getEncounterGeneratorSettings(), filterSpeciesClause);

        Pokemon pokemonModel = this.pokeApiClient.getPokemon(encounter.getNationalDexNumber());

        SquadlockePokemon dummy = new SquadlockePokemon();

        dummy.setModel(pokemonModel);
        dummy.setLocationId(locationId);
        dummy.setEncounteredAt(new Date());
        dummy.setAlive(false);
        dummy.setShiny(false);
        dummy.setAbility(null);
        dummy.setNature(null);
        dummy.setNickname(null);

        participant.getBox().add(dummy);

        this.squadlockeRepository.save(squadlocke);

        return encounter;
    }

    public SquadlockePokemon saveEncounter(String gameId, String participantId, String locationId, String nickname, int abilityIndex, Nature nature,
                                           boolean isShiny) {
        Squadlocke squadlocke = this.getSquadlocke(gameId);

        SquadlockeParticipant participant = squadlocke.getParticipantById(participantId);

        SquadlockePokemon pokemon = participant.getBox().getEncounterForLocation(locationId);

        if(nickname == null) {
            pokemon.setNickname(pokemon.getModel().getName());
        } else {
            // TODO: Do some filtering, we have degenerate friends...
            pokemon.setNickname(nickname);
            pokemon.setAbility(pokemon.getModel().getAbilities().get(abilityIndex));
            pokemon.setNature(nature);
            pokemon.setShiny(isShiny);
            pokemon.setAlive(true);
        }

        this.squadlockeRepository.save(squadlocke);

        return pokemon;
    }
}
