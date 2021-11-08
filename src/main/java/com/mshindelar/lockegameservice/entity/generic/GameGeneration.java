package com.mshindelar.lockegameservice.entity.generic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game-generations")
@Getter
@Setter
public class GameGeneration {
    private int generationId;
    private List<Game> games;
    private List<String> encounters;
    private List<Integer> starterIds;
}
