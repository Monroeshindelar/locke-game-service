package com.mshindelar.lockegameservice.entity.EncounterGenerator;

import java.util.List;
import java.util.Random;

public class EvenEncounterGenerator extends EncounterGenerator {

    private final Random rng;

    public EvenEncounterGenerator() { this.rng = new Random(); }

    @Override
    public Encounter getEncounter(List<Encounter> encounters) {
        int randomIndex = this.rng.nextInt(encounters.size());
        return encounters.get(randomIndex);
    }
}
