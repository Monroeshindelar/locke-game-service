package com.mshindelar.lockegameservice.pokeapi.model;

import lombok.Data;

@Data
public class BaseStats {
    private int total;
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public BaseStats() {
        this(0, 0, 0, 0, 0, 0);
    }

    public BaseStats(int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this((hp + attack + defense + specialAttack + specialDefense + speed), hp, attack, defense,
                specialAttack, specialDefense, speed);
    }

    public BaseStats(int total, int hp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.total = total;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }
}
