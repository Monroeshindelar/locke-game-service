package com.mshindelar.lockegameservice.entity.EncounterGenerator;

import java.util.List;

public abstract class EncounterGenerator {

    public abstract Encounter getEncounter(List<Encounter> encounters);
}
