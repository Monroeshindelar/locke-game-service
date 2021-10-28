package com.mshindelar.lockegameservice.entity.squadlocke;

import lombok.Data;

@Data
public class BoxItem {
    private SquadlockePokemon pokemon;
    private boolean isPlaceholder;
    private boolean caught;
}
