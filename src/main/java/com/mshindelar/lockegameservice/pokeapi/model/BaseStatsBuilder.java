package com.mshindelar.lockegameservice.pokeapi.model;

public class BaseStatsBuilder {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public BaseStatsBuilder withHp(int hp) {
        this.hp = hp;
        return this;
    }

    public BaseStatsBuilder withAttack(int attack) {
        this.attack = attack;
        return this;
    }

    public BaseStatsBuilder withDefense(int defense) {
        this.defense = defense;
        return this;
    }

    public BaseStatsBuilder withSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
        return this;
    }

    public BaseStatsBuilder withSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
        return this;
    }

    public BaseStatsBuilder withSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public BaseStatsBuilder withStat(String name, int value) {
        switch(name) {
            case "hp":
                return this.withHp(value);
            case "attack":
                return this.withAttack(value);
            case "defense":
                return this.withDefense(value);
            case "special-attack":
                return this.withSpecialAttack(value);
            case "special-defense":
                return this.withSpecialDefense(value);
            case "speed":
                return this.withSpeed(value);
            default:
                return null;
        }
    }

    public BaseStats build() {
        return new BaseStats(hp, attack, defense, specialAttack, specialDefense, speed);
    }
}
