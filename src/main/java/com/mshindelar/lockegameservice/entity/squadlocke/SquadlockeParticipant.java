package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class SquadlockeParticipant {
    private String id;
    private PlayerState playerState;
    private SquadlockeTeam team;
    private Pokemon immunitySlot;
    private Set<SquadlockeTeam> previousTeams;
    private int seed;
    private int gameId;

    public SquadlockeParticipant(String id) {
        this.id = id;
        this.playerState = PlayerState.NOT_READY;
        this.team = new SquadlockeTeam();
        this.previousTeams = new HashSet<>();
        this.seed = 0;
    }

    public void ready() {
        setPlayerState(PlayerState.READY);
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
