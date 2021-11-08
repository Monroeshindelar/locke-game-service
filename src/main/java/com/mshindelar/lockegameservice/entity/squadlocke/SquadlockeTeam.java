package com.mshindelar.lockegameservice.entity.squadlocke;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class SquadlockeTeam {
    @JsonIgnore
    private static final int MAX_TEAM_SIZE = 6;
    @JsonIgnore
    private static final int MAX_SIDEBOARD_SIZE = 2;

    private Set<SquadlockePokemon> mainTeam;
    private Set<SquadlockePokemon> sideBoard;
    private SquadlockePokemon immunitySlot;

    public SquadlockeTeam() {
        mainTeam = new HashSet<>();
        sideBoard = new HashSet<>();
        immunitySlot = null;
    }

    public Set<SquadlockePokemon> getMainTeam() { return mainTeam; }

    public Set<SquadlockePokemon> getSideBoard() { return sideBoard; }

    public SquadlockePokemon getImmunitySlot() { return immunitySlot; }

    public boolean addToTeam(SquadlockePokemon pokemon) {
        if(mainTeam.size() == MAX_TEAM_SIZE) return false;
        if(sideBoard.contains(pokemon)) return false;
        if(!pokemon.isAlive()) return false;
        return mainTeam.add(pokemon);
    }

    public boolean addToSideBoard(SquadlockePokemon pokemon) {
        if(sideBoard.size() == MAX_SIDEBOARD_SIZE) return false;
        if(mainTeam.contains(pokemon)) return false;
        return sideBoard.add(pokemon);
    }

    public boolean removeFromTeam(SquadlockePokemon pokemon) { return mainTeam.remove(pokemon); }

    public boolean removeFromSideboard(SquadlockePokemon pokemon) { return sideBoard.remove(pokemon); }

    public boolean setImmunitySlot(SquadlockePokemon pokemon) {
        if(!mainTeam.contains(pokemon) && !sideBoard.contains(pokemon)) return false;
        this.immunitySlot = pokemon;
        return true;
    }
}
