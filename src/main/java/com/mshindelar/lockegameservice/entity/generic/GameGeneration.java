package com.mshindelar.lockegameservice.entity.generic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game-generations")
@Getter
@Setter
public class GameGeneration {
    public int generationId;
    public List<Game> games;
    public List<String> encounters;
}
