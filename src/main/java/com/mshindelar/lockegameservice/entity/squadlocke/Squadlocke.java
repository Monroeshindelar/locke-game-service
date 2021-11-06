package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.state.GameState;
import com.mshindelar.lockegameservice.entity.squadlocke.state.RegistrationGameState;
import com.mshindelar.lockegameservice.exception.GameResourceNotFoundException;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@Document(collection = "squadlocke")
@Data
public class Squadlocke {
    @Id
    private String id;
    private SquadlockeSettings settings;
    private String creatorId;
    private Set<SquadlockeParticipant> participants;
    private GameState gameState;
    private Date createdAt;
    private int encounterTokens;

    public void addParticipant(SquadlockeParticipant squadlockeParticipant) {
        participants.add(squadlockeParticipant);
    }

    public SquadlockeParticipant getParticipantById(String participantId) {
        return participants.stream().filter(participant -> participant.getId().equals(participantId)).findFirst().orElseThrow(() ->
                new GameResourceNotFoundException("Player with id: " + participantId + " is not a participant in this game (id: " + this.getId() + ")"));
    }

    public boolean allPlayersReady() {
        return this.participants.stream()
                .anyMatch(p -> p.getPlayerState() != PlayerState.READY);
    }
}
