package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Box {
    private List<SquadlockePokemon> contents;

    public Box() { this.contents = new LinkedList<>(); }

    public void add(SquadlockePokemon pokemon) { this.contents.add(pokemon); }

    public void remove(SquadlockePokemon pokemon) { this.contents.remove(pokemon); }

    public boolean containsSpecies(int nationalDexNumber) {
        return this.contents.stream()
                .anyMatch(p -> p.getModel().getId() == nationalDexNumber && p.getNickname() != null);
    }

    public boolean containsSpecies(Pokemon pokemon) {
        return containsSpecies(pokemon.getId());
    }

    public boolean containsSpecies(SquadlockePokemon pokemon) {
        return this.containsSpecies(pokemon.getModel());
    }

    public boolean containsEncounterForLocation(String locationId) {
        return this.contents.stream()
                .anyMatch(p -> p.getLocationId().equals(locationId));
    }

    public final List<SquadlockePokemon> getContents() { return this.contents; }

    public final List<SquadlockePokemon> getAlivePokemon() {
        return this.contents.stream()
                .filter(SquadlockePokemon::isAlive)
                .collect(Collectors.toList());
    }

    public final List<String> getEncounterLocations() {
        return this.contents.stream()
                .map(SquadlockePokemon::getLocationId)
                .collect(Collectors.toList());
    }

    public SquadlockePokemon getEncounterForLocation(String locationId) {
        return this.contents.stream()
                .filter(p -> p.getLocationId().equals(locationId))
                .findFirst()
                .orElse(null);
    }
}
