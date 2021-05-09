package com.mshindelar.lockegameservice.entity.squadlocke;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mshindelar.lockegameservice.entity.generic.pokemon.Pokemon;

import java.util.HashSet;
import java.util.Set;

public class SquadlockeTeam {
    @JsonIgnore
    private static final int MAX_TEAM_SIZE = 6;
    @JsonIgnore
    private static final int MAX_SIDEBOARD_SIZE = 2;

    private Set<Pokemon> mainTeam;
    private Set<Pokemon> sideBoard;
    private Pokemon immunitySlot;

    public SquadlockeTeam() {
        mainTeam = new HashSet<>();
        sideBoard = new HashSet<>();
        immunitySlot = null;
    }

    public Set<Pokemon> getMainTeam() { return mainTeam; }

    public Set<Pokemon> getSideBoard() { return sideBoard; }

    public Pokemon getImmunitySlot() { return immunitySlot; }

    public boolean addToTeam(Pokemon pokemon) {
        if(mainTeam.size() == MAX_TEAM_SIZE) return false;
        if(sideBoard.contains(pokemon)) return false;
        return mainTeam.add(pokemon);
    }

    public boolean addToSideBoard(Pokemon pokemon) {
        if(sideBoard.size() == MAX_SIDEBOARD_SIZE) return false;
        if(mainTeam.contains(pokemon)) return false;
        return sideBoard.add(pokemon);
    }

    public boolean removeFromTeam(Pokemon pokemon) { return mainTeam.remove(pokemon); }

    public boolean removeFromSideboard(Pokemon pokemon) { return sideBoard.remove(pokemon); }

    public boolean setImmunitySlot(Pokemon pokemon) {
        if(!mainTeam.contains(pokemon) && !sideBoard.contains(pokemon)) return false;
        this.immunitySlot = pokemon;
        return true;
    }
}
