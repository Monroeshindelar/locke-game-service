package com.mshindelar.lockegameservice.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Ability {
    @JsonProperty("is_hidden")
    private boolean isHidden;
    private String name;

    @SuppressWarnings("unchecked")
    @JsonProperty("ability")
    private void unpackNested(Map<String, Object> ability) {
        this.name = (String) ability.get("name");
    }
}
