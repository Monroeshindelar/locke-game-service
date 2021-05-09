package com.mshindelar.lockegameservice.entity.squadlocke;

import com.mshindelar.lockegameservice.entity.squadlocke.configuration.SquadlockeSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "squadlocke")
@Getter
@Setter
public class Squadlocke {
    @Id
    private String id;
    private SquadlockeSettings settings;
    private SquadlockeParticipant creator;
    private Set<SquadlockeParticipant> participants;
    private Date createdAt;

    private Squadlocke(SquadlockeParticipant creator, SquadlockeSettings squadlockeSettings) {
        this.settings = squadlockeSettings;
        this.creator = creator;
        this.participants = new HashSet<>();
        this.addParticipant(creator);
    }

    public static Squadlocke create(SquadlockeParticipant creator, SquadlockeSettings squadlockeSettings) {
        return new Squadlocke(creator, squadlockeSettings);
    }

    public void addParticipant(SquadlockeParticipant squadlockeParticipant) {
        participants.add(squadlockeParticipant);
    }

    public SquadlockeParticipant getParticipantById(String participantId) {
        return participants.stream().filter(participant -> participant.getId().equals(participantId)).findFirst().orElse(null);
    }
}
