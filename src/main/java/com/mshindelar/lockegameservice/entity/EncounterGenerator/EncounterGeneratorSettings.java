package com.mshindelar.lockegameservice.entity.EncounterGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncounterGeneratorSettings {
    private EncounterProbability encounterProbability;

    public EncounterGeneratorSettings() { this.encounterProbability = EncounterProbability.DEFAULT; }
}
