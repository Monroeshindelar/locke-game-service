package com.mshindelar.lockegameservice.entity.squadlocke.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameState {
    private GameStateType gameStateType;

    public GameState(GameStateType gameStateType) {
        this.gameStateType = gameStateType;
    }
}
