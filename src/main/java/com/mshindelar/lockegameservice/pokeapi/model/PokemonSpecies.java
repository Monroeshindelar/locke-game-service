package com.mshindelar.lockegameservice.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class PokemonSpecies {
    private int id;
    private String name;
    @JsonProperty("gender_rate")
    private int genderRate;
    @JsonProperty("has_gender_differences")
    private boolean hasGenderDifferences;
    private int evolutionChainId;

    @JsonProperty("evolution_chain")
    public void parseEvolutionChainId(Map<String, Object> evolutionChain) {
        String url = (String) evolutionChain.get("url");

        String[] tokens = url.split("/");

        this.evolutionChainId = Integer.parseInt(tokens[tokens.length - 1]);
    }
}
