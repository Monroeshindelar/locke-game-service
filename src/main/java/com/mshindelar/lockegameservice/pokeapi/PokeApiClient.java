package com.mshindelar.lockegameservice.pokeapi;

import com.mshindelar.lockegameservice.pokeapi.model.EvolutionChain;
import com.mshindelar.lockegameservice.pokeapi.model.Pokemon;
import com.mshindelar.lockegameservice.pokeapi.model.PokemonSpecies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class PokeApiClient {

    private static final String API_BASE_URL = "http://pokeapi.co/api/v2/";
    private static final String POKEMON_ENDPOINT_URL = API_BASE_URL + "pokemon/";
    private static final String POKEMON_SPECIES_ENDPOINT_URL = API_BASE_URL + "pokemon-species/";
    private static final String EVOLUTION_CHAIN_ENDPOINT_URL = API_BASE_URL + "evolution-chain/";

    @Autowired
    private RestTemplate restTemplate;

    public Pokemon getPokemon(int nationalDexNumber) {
        String uri = POKEMON_ENDPOINT_URL + nationalDexNumber;
        return this.restTemplate.getForObject(uri, Pokemon.class);
    }

    public Pokemon getPokemon(String name) {
        String uri = POKEMON_ENDPOINT_URL + name;
        return this.restTemplate.getForObject(uri, Pokemon.class);
    }

    public PokemonSpecies getPokemonSpecies(int speciesId) {
        String uri = POKEMON_SPECIES_ENDPOINT_URL + speciesId;
        return this.restTemplate.getForObject(uri, PokemonSpecies.class);
    }

    public EvolutionChain getEvolutionChain(int evolutionChainId) {
        String uri = EVOLUTION_CHAIN_ENDPOINT_URL + evolutionChainId;
        return this.restTemplate.getForObject(uri, EvolutionChain.class);
    }
}
