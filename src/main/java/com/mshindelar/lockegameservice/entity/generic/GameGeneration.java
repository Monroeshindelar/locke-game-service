package com.mshindelar.lockegameservice.entity.generic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game-generations")
public class GameGeneration {
    public int generationId;
    public List<Game> games;
    public List<String> encounters;
}
