package com.mshindelar.lockegameservice.entity.generic.pokemon;

import lombok.Data;

@Data
public class BaseStats {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
}
