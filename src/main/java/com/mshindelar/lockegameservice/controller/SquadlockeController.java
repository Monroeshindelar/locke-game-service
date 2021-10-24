package com.mshindelar.lockegameservice.controller;

import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;
import com.mshindelar.lockegameservice.entity.squadlocke.Squadlocke;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeParticipant;
import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.service.SquadlockeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/games/squadlocke")
@Slf4j
public class SquadlockeController {
    @Autowired
    private SquadlockeService squadlockeService;

    private static Logger logger = LoggerFactory.getLogger(SquadlockeController.class);

    @PutMapping("create")
    private Squadlocke create(@RequestParam(name = "participantId") String participantId, @RequestBody SquadlockeSettings squadlockeSettings) {
        return this.squadlockeService.createSquadlocke(participantId, squadlockeSettings);
    }

    @GetMapping("{gameId}")
    private Squadlocke get(@PathVariable("gameId") String gameId, @RequestHeader Map<String, String> headers) {
        return this.squadlockeService.getSquadlocke(gameId);
    }

    @PostMapping("{gameId}/join")
    private Squadlocke join(@PathVariable(name = "gameId") String gameId, @RequestParam(name = "participantId") String participantId) {
        return this.squadlockeService.joinSquadlocke(gameId, participantId);
    }

    @PostMapping("{gameId}/start")
    private Squadlocke start(@PathVariable("gameId") String gameId) {
        return this.squadlockeService.startSquadlocke(gameId);
    }

    @PostMapping("{gameId}/finalize")
    private Squadlocke finalize(@PathVariable("gameId") String gameId) {
        return this.squadlockeService.finalizeSquadlocke(gameId);
    }

    @GetMapping("{gameId}/participants/{participantId}")
    private SquadlockeParticipant getParticipant(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId) {
        return this.squadlockeService.getParticipant(gameId, participantId);
    }

    @PostMapping("{gameId}/participants/{participantId}/ready")
    private SquadlockeParticipant ready(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId) {
        return this.squadlockeService.readyParticipant(gameId, participantId);
    }

    @PostMapping("{gameId}/participants/{participantId}/team/main/add")
    private SquadlockeParticipant addToTeam(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId, @RequestBody Pokemon pokemon) {
        return this.squadlockeService.addPokemonToTeam(gameId, participantId, pokemon);
    }

    @PostMapping("{gameId}/participants/{participantId}/team/main/remove")
    private SquadlockeParticipant removeFromTeam(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                 @RequestParam(name = "nationalDexNumber") int nationalDexNumber) {
        return this.squadlockeService.removePokemonFromTeam(gameId, participantId, nationalDexNumber);
    }

    @PostMapping("{gameId}/participants/{participantId}/team/side/add")
    private SquadlockeParticipant addToSideBoard(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                 @RequestBody Pokemon pokemon) {
        return this.squadlockeService.addPokemonToSideboard(gameId, participantId, pokemon);
    }

    @PostMapping("{gameId}/participants/{participantId}/team/side/remove")
    private SquadlockeParticipant removeFromSideBoard(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                      @RequestParam(name = "nationalDexNumber") int nationalDexNumber) {
        return this.squadlockeService.removePokemonFromSideboard(gameId, participantId, nationalDexNumber);
    }

    @PostMapping("{gameId}/participants/{participantId}/team/immunity")
    private SquadlockeParticipant setImmunitySlot(@PathVariable("gameId") String gameId, @PathVariable("participantId") String participantId,
                                                  @RequestBody Pokemon pokemon) {
        return this.squadlockeService.setImmunitySlot(gameId, participantId, pokemon);
    }

    @GetMapping("/by-userid/{userId}")
    private List<Squadlocke> getSquadlockeByUserId(@PathVariable("userId") String userId) {
        return this.squadlockeService.getSquadlockeByUserId(userId);
    }

    @GetMapping("/joinable")
    private List<Squadlocke> getJoinableGames(@RequestParam("userId") String userId) {
        return this.squadlockeService.getJoinableGames(userId);
    }
}
