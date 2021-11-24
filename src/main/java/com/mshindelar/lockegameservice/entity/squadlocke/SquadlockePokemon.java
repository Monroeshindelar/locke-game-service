package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.pokeapi.model.Ability;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import com.mshindelar.lockegameservice.pokeapi.model.PokemonSpecies;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class SquadlockePokemon {
    private Pokemon model;
    private PokemonSpecies species;
    private boolean isAlive;
    private boolean isShiny;
    private String nickname;
    private Nature nature;
    private Gender gender;
    private Ability ability;
    private String locationId;
    private Date encounteredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SquadlockePokemon that = (SquadlockePokemon) o;
        return isAlive == that.isAlive && isShiny == that.isShiny && Objects.equals(model, that.model) && Objects.equals(species, that.species) && Objects.equals(nickname, that.nickname) && nature == that.nature && gender == that.gender && Objects.equals(ability, that.ability) && Objects.equals(locationId, that.locationId) && Objects.equals(encounteredAt, that.encounteredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, species, isAlive, isShiny, nickname, nature, gender, ability, locationId, encounteredAt);
    }
}
