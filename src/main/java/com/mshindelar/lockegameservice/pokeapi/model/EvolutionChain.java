package com.mshindelar.lockegameservice.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockePokemon;
import lombok.Data;

import java.util.List;

@Data
public class EvolutionChain {
    private int id;

    @JsonProperty("chain")
    private EvolutionChainItem chain;

    public List<EvolutionChainItem> getNextEvolution(SquadlockePokemon pokemon) {
        return this.getNextEvolution(pokemon.getModel());
    }

    public List<EvolutionChainItem> getNextEvolution(Pokemon pokemon) {
        return this.getNextEvolution(pokemon.getName());
    }

    public List<EvolutionChainItem> getNextEvolution(String name) {
        return this.getNextEvolution(chain, name);
    }

    private List<EvolutionChainItem> getNextEvolution(EvolutionChainItem item, String name) {
        if(item == null) {
            return null;
        }

        if(item.getName().equals(name)) {
            return item.getEvolvesTo();
        }

        for(EvolutionChainItem eci : item.getEvolvesTo()) {
            return getNextEvolution(eci, name);
        }

        return null;
    }
}
