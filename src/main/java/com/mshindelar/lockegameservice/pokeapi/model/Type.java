package com.mshindelar.lockegameservice.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Type {
    private String name;

    @SuppressWarnings("unchecked")
    @JsonProperty("type")
    private void unpackTypeName(Map<String, Object> type) {
        this.name = (String) type.get("name");
    }
}
