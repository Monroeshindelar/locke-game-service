package com.mshindelar.lockegameservice.entity.squadlocke.configuration;

import com.mshindelar.lockegameservice.entity.generic.GameGeneration;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeDifficultyMode;
import com.mshindelar.lockegameservice.entity.squadlocke.state.Accessibility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadlockeSettings {
    private String name;
    //private GameGeneration generation;
    private int generationId;
    private int checkpointFrequency;
    private double voteThreshhold;
    private SquadlockeDifficultyMode difficultyMode;
    private TournamentSettings tournamentSettings;
    private Accessibility accessibility;

    public SquadlockeSettings() {
        this.generationId = -1;
        this.checkpointFrequency = 2;
        this.voteThreshhold = 51;
        this.difficultyMode = SquadlockeDifficultyMode.EASY;
        this.tournamentSettings = new TournamentSettings();
        this.accessibility = Accessibility.INVITE;
    }

    public TournamentSettings getTournamentSettings() { return new TournamentSettings(this.tournamentSettings); }
}
