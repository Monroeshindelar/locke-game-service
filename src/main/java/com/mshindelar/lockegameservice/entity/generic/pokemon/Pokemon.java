package com.mshindelar.lockegameservice.entity.generic.pokemon;

import lombok.Data;

import java.util.Objects;

@Data
public class Pokemon {
    private int nationalDexNumber;
    private String name;
    private Type primaryType;
    private Type secondaryType;
    private Gender gender;
    private BaseStats stats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return nationalDexNumber == pokemon.nationalDexNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalDexNumber);
    }
}
