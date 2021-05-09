package com.mshindelar.lockegameservice.entity.squadlocke.configuration;

import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeDifficultyMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadlockeSettings {
    private GameGeneration generation;
    private SquadlockeDifficultyMode difficultyMode;
    private TournamentSettings tournamentSettings;
}
