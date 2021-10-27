package com.mshindelar.lockegameservice.entity.EncounterGenerator;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "encounters")
@Data
public class Encounter {
    private String generationId;
    private int gameId;
    private String locationId;
    private EncounterMode mode;
    private int nationalDexNumber;
    private double defaultEncounterRate;
}
