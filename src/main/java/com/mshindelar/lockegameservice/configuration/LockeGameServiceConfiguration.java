package com.mshindelar.lockegameservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="application")
@Getter
@Setter
public class LockeGameServiceConfiguration {
    private TournamentProperties tournament = new TournamentProperties();

    @Getter
    @Setter
    public static class TournamentProperties {
        private String key;
        private String uri;
    }
}
