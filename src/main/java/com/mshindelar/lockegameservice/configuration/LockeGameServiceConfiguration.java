package com.mshindelar.lockegameservice.configuration;

import com.mshindelar.lockegameservice.entity.EncounterGenerator.EncounterGeneratorFactory;
import com.mshindelar.lockegameservice.pokeapi.PokeApiClient;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="application")
@Getter
@Setter
public class LockeGameServiceConfiguration {
    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Bean
    public PokeApiClient pokeApiClient() { return new PokeApiClient(); }

    @Bean
    public EncounterGeneratorFactory encounterGeneratorFactory() { return new EncounterGeneratorFactory(); }

    private TournamentProperties tournament = new TournamentProperties();

    @Getter
    @Setter
    public static class TournamentProperties {
        private String key;
        private String uri;
    }
}
