package com.mshindelar.lockegameservice.entity.generic.challonge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.Date;

@JsonRootName(value = "match")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Match {
    @JsonProperty("attachment_count")
    private int attachmentCount;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("group_id")
    private int groupId;
    @JsonProperty("has_attachment")
    private boolean hasAttachment;
    private int id;
    private String identifier;
    private String location;
    @JsonProperty("loser_id")
    private int loserId;
    @JsonProperty("player1_id")
    private int player1Id;
    @JsonProperty("player1_is_prereq_match_loser")
    private boolean player1IsPrereqMatchLoser;
    @JsonProperty("player1_votes")
    private int player1Votes;
    @JsonProperty("player2_id")
    private int player2Id;
    @JsonProperty("player2_is_prereq_match_loser")
    private boolean player2IsPrereqMatchLoser;
    @JsonProperty("player2_votes")
    private int player2Votes;
    private int round;
    @JsonProperty("scheduled_time")
    private Date scheduledTime;
    @JsonProperty("started_at")
    private Date startedAt;
    private String state;
    @JsonProperty("tournament_id")
    private int tournamentId;
//    @JsonProperty("underway_at")
//    private Date underwayAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("winner_id")
    private int winnerId;
    @JsonProperty("prerequisite_match_ids_csv")
    private String prerequisiteMatchIdsCsv;
    @JsonProperty("scores_csv")
    private String scoresCsv;


}
