package com.mshindelar.lockegameservice.entity.EncounterGenerator;

public class EncounterGeneratorFactory {

    public EncounterGenerator getEncounterGenerator(EncounterProbability encounterProbability) {
        switch(encounterProbability) {
            case EVEN:
                return new EvenEncounterGenerator();
            case DEFAULT:
            default:
                return new WeightedEncounterGenerator();
        }
    }
}
