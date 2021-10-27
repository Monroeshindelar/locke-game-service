package com.mshindelar.lockegameservice.entity.squadlocke;

import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class SquadlockeParticipant {
    private String id;
    private PlayerState playerState;
    private SquadlockeTeam team;
    private SquadlockePokemon immunitySlot;
    private Set<SquadlockeTeam> previousTeams;
    private Box box;
    private int seed;
    private int gameId;
    private int usedEncounterTokens;

    public SquadlockeParticipant(String id) {
        this.id = id;
        this.playerState = PlayerState.NOT_READY;
        this.team = new SquadlockeTeam();
        this.previousTeams = new HashSet<>();
        this.box = new Box();
        this.seed = 0;
        this.usedEncounterTokens = 0;
    }

    public void readyUp() {
        this.setPlayerState(PlayerState.READY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadlockeParticipant that = (SquadlockeParticipant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
