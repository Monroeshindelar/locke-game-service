package com.mshindelar.lockegameservice.entity.squadlocke.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mshindelar.lockegameservice.entity.squadlocke.state.TournamentGameState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class TournamentSettings {
    private String name;
    @JsonProperty("tournament_type")
    private String tournamentType;
    private String url;
    private String subdomain;
    private String description;
    @JsonProperty("open_signup")
    private Boolean openSignup;
    @JsonProperty("hold_third_place_match")
    private Boolean holdThirdPlaceMatch;
    @JsonProperty("pts_for_match_win")
    private Double ptsForMatchWin;
    @JsonProperty("pts_for_match_tie")
    private Double ptsForMatchTie;
    @JsonProperty("pts_for_game_win")
    private Double ptsForGameWin;
    @JsonProperty("pts_for_game_tie")
    private Double ptsForGameTie;
    @JsonProperty("pts_for_bye")
    private Double ptsForBye;
    @JsonProperty("swiss_rounds")
    private Integer swissRounds;
    @JsonProperty("ranked_by")
    private String rankedBy;
    @JsonProperty("rr_pts_for_game_win")
    private Double rrPtsForGameWin;
    @JsonProperty("rr_pts_for_game_tie")
    private Double rrPtsForGameTie;
    @JsonProperty("rr_pts_for_match_win")
    private Double rrPtsForMatchWin;
    @JsonProperty("rr_pts_for_match_tie")
    private Double rrPtsForMatchTie;
    @JsonProperty("accept_attachments")
    private Boolean acceptAttachments;
    @JsonProperty("hide_forum")
    private Boolean hideForum;
    @JsonProperty("show_rounds")
    private Boolean showRounds;
    @JsonProperty("private")
    private Boolean isPrivate;
    @JsonProperty("notify_users_when_matches_open")
    private Boolean notifyUsersWhenMatchesOpen;
    @JsonProperty("notify_users_when_the_tournament_ends")
    private Boolean notifyUsersWhenTournamentEnds;
    @JsonProperty("sequential_pairings")
    private Boolean sequentialPairings;
    @JsonProperty("signup_cap")
    private Integer signupCap;
    @JsonProperty("start_at")
    private Date startAt;
    @JsonProperty("check_in_duration")
    private String checkInDuration;
    @JsonProperty("grand_finals_modifier")
    private String grandFinalsModifier;

    public TournamentSettings() {}

    public TournamentSettings(TournamentSettings toCopy) {
        this.name = toCopy.name;
        this.tournamentType = toCopy.tournamentType;
        this.url = toCopy.url;
        this.subdomain = toCopy.subdomain;
        this.description = toCopy.description;
        this.openSignup = toCopy.openSignup;
        this.holdThirdPlaceMatch = toCopy.holdThirdPlaceMatch;
        this.ptsForMatchWin = toCopy.ptsForMatchWin;
        this.ptsForMatchTie = toCopy.ptsForMatchTie;
        this.ptsForGameWin = toCopy.ptsForGameWin;
        this.ptsForGameTie = toCopy.ptsForGameTie;
        this.ptsForBye = toCopy.ptsForBye;
        this.swissRounds = toCopy.swissRounds;
        this.rankedBy = toCopy.rankedBy;
        this.rrPtsForMatchWin = toCopy.rrPtsForMatchWin;
        this.rrPtsForMatchTie = toCopy.rrPtsForMatchTie;
        this.rrPtsForGameWin = toCopy.rrPtsForGameWin;
        this.rrPtsForGameTie = toCopy.rrPtsForGameTie;
        this.acceptAttachments = toCopy.acceptAttachments;
        this.hideForum = toCopy.hideForum;
        this.showRounds = toCopy.showRounds;
        this.isPrivate = toCopy.isPrivate;
        this.notifyUsersWhenMatchesOpen = toCopy.notifyUsersWhenMatchesOpen;
        this.notifyUsersWhenTournamentEnds = toCopy.notifyUsersWhenTournamentEnds;
        this.sequentialPairings = toCopy.sequentialPairings;
        this.signupCap = toCopy.signupCap;
        this.startAt = toCopy.startAt;
        this.checkInDuration = toCopy.checkInDuration;
        this.grandFinalsModifier = toCopy.grandFinalsModifier;
    }
}
