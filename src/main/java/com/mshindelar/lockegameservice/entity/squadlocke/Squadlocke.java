package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import com.mshindelar.lockegameservice.entity.squadlocke.state.GameState;
import com.mshindelar.lockegameservice.entity.squadlocke.state.RegistrationGameState;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "squadlocke")
@Data
public class Squadlocke {
    @Id
    private String id;
    private SquadlockeSettings settings;
    private SquadlockeParticipant creator;
    private Set<SquadlockeParticipant> participants;
    private GameState gameState;
    private Date createdAt;

    public void addParticipant(SquadlockeParticipant squadlockeParticipant) {
        participants.add(squadlockeParticipant);
    }

    public SquadlockeParticipant getParticipantById(String participantId) {
        return participants.stream().filter(participant -> participant.getId().equals(participantId)).findFirst().orElse(null);
    }

    public boolean allPlayersReady() {
        for(SquadlockeParticipant participant : participants) {
            if(participant.getPlayerState() != PlayerState.READY) return false;
        }
        return true;
    }
}
