package com.mshindelar.lockegameservice.entity.squadlocke.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckpointGameState extends GameState {
    private int checkpointId;

    public CheckpointGameState(int checkpointId) {
        super(GameStateType.CHECKPOINT);
        this.checkpointId = checkpointId;
    }
}
