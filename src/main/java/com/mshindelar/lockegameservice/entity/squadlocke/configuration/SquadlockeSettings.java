package com.mshindelar.lockegameservice.entity.squadlocke.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mshindelar.lockegameservice.entity.squadlocke.SquadlockeDifficultyMode;
import com.mshindelar.lockegameservice.entity.squadlocke.state.Accessibility;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SquadlockeSettings {
    private String name;
    private int generationId;
    private int checkpointFrequency;
    private double voteThreshold;
    private SquadlockeDifficultyMode difficultyMode;
    private TournamentSettings tournamentSettings;
    private Accessibility accessibility;

    @JsonCreator
    public SquadlockeSettings(@JsonProperty(value = "generationId", required = true) int generationId) {
        this.generationId = generationId;
        this.checkpointFrequency = 2;
        this.voteThreshold = 51;
        this.difficultyMode = SquadlockeDifficultyMode.EASY;
        this.tournamentSettings = new TournamentSettings();
        this.accessibility = Accessibility.INVITE;
    }

    public TournamentSettings getTournamentSettings() { return new TournamentSettings(this.tournamentSettings); }
}
