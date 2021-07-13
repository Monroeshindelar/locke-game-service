package com.mshindelar.lockegameservice.entity.squadlocke.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentGameState extends GameState {
    private int tournamentId;

    public TournamentGameState(int tournamentId) {
        super(GameStateType.TOURNAMENT);
        this.tournamentId = tournamentId;
    }
}
