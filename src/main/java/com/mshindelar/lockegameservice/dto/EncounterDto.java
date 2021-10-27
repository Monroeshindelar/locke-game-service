package com.mshindelar.lockegameservice.dto;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterMode;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import lombok.Data;

@Data
public class EncounterDto {
    private String locationId;
    private EncounterMode mode;
    private int nationalDexNumber;
    private double defaultEncounterRate;
    private Pokemon pokemonModel;
}
