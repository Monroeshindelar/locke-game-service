package com.mshindelar.lockegameservice.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EvolutionChainItem {
    private String name;
    @JsonProperty("evolves_to")
    private List<EvolutionChainItem> evolvesTo;
    @JsonProperty("is_baby")
    private boolean isBaby;

    @JsonProperty("species")
    public void pullSpeciesName(Map<String, Object> species) {
        this.name = (String) species.get("name");
    }
}
