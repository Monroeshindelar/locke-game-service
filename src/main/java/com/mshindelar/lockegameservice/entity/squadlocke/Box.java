package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.exception.DuplicateEncounterException;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import com.mshindelar.lockegameservice.pokeapi.model.PokemonSpecies;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Box {
    private List<BoxItem> contents;

    public Box() { this.contents = new LinkedList<>(); }

    /**
     * Adds pokemon to box.
     *
     * Fails if the user is trying to get an encounter
     * for a location they already have a pokemon for,
     * with the exception of if that pokemon meets the
     * species clause requirements
     *
     * @throws DuplicateEncounterException If users tries to add
     *  a pokemon that has a location id that matches another pokemon
     *  in the box.
     */
    public void add(SquadlockePokemon pokemon) {
        BoxItem item = new BoxItem();
        item.setPokemon(pokemon);
        if(this.containsEncounterForLocation(pokemon.getLocationId())) {
            BoxItem i = this.getBoxItemForLocation(pokemon.getLocationId());

            // Checks to see if the user had a valid species clause
            // placeholder that we should overwrite
            if(i.isPlaceholder() && this.containsSpecies(i.getPokemon())) {
                this.remove(i.getPokemon());
            } else {
                throw new DuplicateEncounterException("Cannot catch pokemon. Encounter for " + pokemon.getLocationId() + " already exists");
            }
        }

        // If the encounter is something that the user hasn't already caught,
        // it can automatically be flagged as not a placeholder.
        // Placeholder indicates that the user encountered a pokemon that they
        // have already caught and can reroll.
        item.setPlaceholder(this.containsSpecies(pokemon.getSpecies().getEvolutionChainId()));

        item.setCaught(false);

        this.contents.add(item);
    }

    /**
     * Removes pokemon from box.
     */
    public void remove(SquadlockePokemon pokemon) {
        BoxItem match = this.contents.stream()
                .filter(i -> i.getPokemon().equals(pokemon))
                .findFirst()
                .orElse(null);

        this.contents.remove(match);
    }

    /**
     * Searches box for caught pokemon with the same national dex number.
     *
     * Ignores pokemon that are not caught or that are species clause
     * placeholders
     *
     * @return True if a pokemon with the national dex number that has been caught
     * is found. False if no such pokemon exists in the box.
     */
    public boolean containsSpecies(int evolutionChainId) {
        return this.contents.stream()
                .filter(i -> !i.isPlaceholder() && i.isCaught())
                .anyMatch(i -> i.getPokemon().getSpecies().getEvolutionChainId() == evolutionChainId);
    }

    public boolean containsSpecies(SquadlockePokemon pokemon) { return this.containsSpecies(pokemon.getSpecies().getEvolutionChainId()); }

    /**
     * Searches box to see if there is a pokemon that has been encountered
     * on a specified route.
     */
    public boolean containsEncounterForLocation(String locationId) {
        return this.contents.stream()
                .anyMatch(i -> i.getPokemon().getLocationId().equals(locationId));
    }

    /**
     * Gets all encounters from the box whether they have been caught or not
     */
    public final List<SquadlockePokemon> getContents() {
        return this.contents.stream()
                .map(BoxItem::getPokemon)
                .collect(Collectors.toList());
    }

    /**
     * Gets all pokemon that are still alive from the box
     */
    public final List<SquadlockePokemon> getAlivePokemon() {
        return this.contents.stream()
                .map(BoxItem::getPokemon)
                .filter(SquadlockePokemon::isAlive)
                .collect(Collectors.toList());
    }

    /**
     * Gets all dead pokemon in the box
     */
    public final List<SquadlockePokemon> getDeadPokemon() {
        return this.contents.stream()
                .map(BoxItem::getPokemon)
                .filter(p -> !p.isAlive())
                .collect(Collectors.toList());
    }

    /**
     * Gets all encounters that were not caught
     */
    public final List<SquadlockePokemon> getUncaughtPokemon() {
        return this.contents.stream()
                .filter(i -> !i.isCaught())
                .map(BoxItem::getPokemon)
                .collect(Collectors.toList());
    }

    /**
     * Gets list of all encounters for location.
     * Filters out locations for pokemon that are placeholders because
     * they can be re-rolled
     */
    public final List<String> getEncounterLocations() {
        return this.contents.stream()
                .filter(i -> !i.isPlaceholder())
                .map(BoxItem::getPokemon)
                .map(SquadlockePokemon::getLocationId)
                .collect(Collectors.toList());
    }

    /**
     * Gets the encounter for a particular location.
     */
    public SquadlockePokemon getEncounterForLocation(String locationId) {
        return this.contents.stream()
                .map(BoxItem::getPokemon)
                .filter(p -> p.getLocationId().equals(locationId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates box information for a particular encounter
     */
    public void updateEncounter(String locationId, String nickname, int abilityIndex, Nature nature, Gender gender, boolean isShiny) {
        BoxItem item = this.getBoxItemForLocation(locationId);

        if(item == null) return;

        item.setPlaceholder(false);
        item.setCaught(true);
        item.getPokemon().setAlive(true);
        item.getPokemon().setNickname(nickname);
        item.getPokemon().setAbility(item.getPokemon().getModel().getAbilities().get(abilityIndex));
        item.getPokemon().setNature(nature);
        item.getPokemon().setGender(gender);
        item.getPokemon().setShiny(isShiny);
    }

    /**
     * Updates the alive status of a pokemon in the box
     */
    public void killPokemon(String locationId) {
        SquadlockePokemon pokemon = this.getEncounterForLocation(locationId);
        pokemon.setAlive(false);
    }

    /**
     * Gets the box item for a location.
     */
    private BoxItem getBoxItemForLocation(String locationId) {
        return this.contents.stream()
                .filter(i -> i.getPokemon().getLocationId().equals(locationId))
                .findFirst()
                .orElse(null);
    }
}
