package com.mshindelar.lockegameservice.entity.EncounterGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "encounters")
@Data
public class Encounter {
    private String generationId;
    private int gameId;
    private String locationId;
    private EncounterMode encounterMode;
    private int nationalDexNumber;
    private double defaultEncounterRate;
    private Pokemon model;
}
