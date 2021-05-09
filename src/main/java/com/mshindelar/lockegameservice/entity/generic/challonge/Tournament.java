package com.mshindelar.lockegameservice.entity.generic.challonge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@JsonRootName(value = "tournament")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Tournament {
    private int id;
    private String name;
    private String url;
    private String description;
    @JsonProperty("tournament_type")
    private String tournamentType;
    @JsonProperty("started_at")
    private Date startedAt;
    @JsonProperty("completed_at")
    private Date completedAt;
    @JsonProperty("require_score_agreement")
    private boolean requireScoreAgreement;
    @JsonProperty("notify_users_when_matches_open")
    private boolean notifyUsersWhenMatchesOpen;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    private String state;
    @JsonProperty("open_signup")
    private boolean openSignup;
    @JsonProperty("notify_users_when_the_tournament_ends")
    private boolean notifyUsersWhenTournamentEnds;
    @JsonProperty("progress_meter")
    private int progressMeter;
    @JsonProperty("quick_advance")
    private boolean quickAdvance;
    @JsonProperty("hold_third_place_match")
    private boolean holdThirdPlaceMatch;
    @JsonProperty("pts_for_game_win")
    private double ptsForGameWin;
    @JsonProperty("pts_for_game_tie")
    private double ptsForGameTie;
    @JsonProperty("pts_for_match_win")
    private double ptsForMatchWin;
    @JsonProperty("pts_for_match_tie")
    private double ptsForMatchTie;
    @JsonProperty("swiss_rounds")
    private int swissRounds;
    @JsonProperty("private")
    private boolean isPrivate;
    @JsonProperty("ranked_by")
    private String rankedBy;
    @JsonProperty("show_rounds")
    private boolean showRounds;
    @JsonProperty("hide_forum")
    private boolean hideForum;
    @JsonProperty("sequential_pairings")
    private boolean sequentialPairings;
    @JsonProperty("accept_attachments")
    private boolean acceptAttachments;
    @JsonProperty("rr_pts_for_game_win")
    private double rrPtsForGameWin;
    @JsonProperty("rr_pts_for_game_tie")
    private double rrPtsForGameTie;
    @JsonProperty("rr_pts_for_match_win")
    private double rrPtsForMatchWin;
    @JsonProperty("rr_pts_for_match_tie")
    private double rrPtsForMatchTie;
    @JsonProperty("created_by_api")
    private boolean createdByApi;
    @JsonProperty("credit_capped")
    private boolean creditCapped;
    private String category;
    @JsonProperty("hide_seeds")
    private boolean hideSeeds;
    @JsonProperty("prediction_method")
    private int predictionMethod;
    @JsonProperty("predictions_opened_at")
    private int predictionsOpenedAt;
    @JsonProperty("anonymousVoting")
    private boolean anonymousVoting;
    @JsonProperty("max_predictions_per_user")
    private int maxPredictionsPerUser;
    @JsonProperty("signup_cap")
    private int signupCap;
    @JsonProperty("game_id")
    private int gameId;
    @JsonProperty("participants_count")
    private int participantsCount;
    @JsonProperty("group_stages_enabled")
    private boolean groupStagesEnabled;
    @JsonProperty("allow_participant_match_reporting")
    private boolean allowParticipantMatchReporting;
    private boolean teams;
    @JsonProperty("check_in_duration")
    private String checkInDuration;
    @JsonProperty("start_at")
    private Date startAt;
    @JsonProperty("started_checking_in_at")
    private Date startedCheckingInAt;
//    @JsonProperty("tie_breaks")
//    private String[] tieBreaks;
    @JsonProperty("locked_at")
    private Date lockedAt;
    @JsonProperty("event_id")
    private int eventId;
    @JsonProperty("pubilc_predictions_before_start_time")
    private boolean publicPredictionsBeforeStartTime;
    private boolean ranked;
//    @JsonProperty("grand_finals_modifier")
//    private String grandFinalsModifier;
    @JsonProperty("predict_the_losers_bracket")
    private boolean predictLosersBracket;
//    private String spam;
//    private String ham;
    @JsonProperty("rr_iterations")
    private int rrIterations;
    @JsonProperty("tournament_registration_id")
    private int registrationId;
//    @JsonProperty("donation_contest_enabled")
//    private String donationContestEnabled;
//    @JsonProperty("mandatoryDonation")
//    private String mandatoryDonation;
//    @JsonProperty("non_elimination_tournament_data")
//    private String nonEliminationTournamentData;
//    @JsonProperty("auto_assign_stations")
//    private String autoAssignStations;
//    @JsonProperty("only_start_matches_with_stations")
//    private String onlyStartMatchesWithStations;
    @JsonProperty("registration_fee")
    private double registrationFee;
    @JsonProperty("registration_type")
    private String registrationType;
    @JsonProperty("split_participants")
    private boolean splitParticipants;
    @JsonProperty("allowed_regions")
    private List<String> allowedRegions;
//    @JsonProperty("show_participant_country")
//    private boolean showParticipantCountry;
    @JsonProperty("program_id")
    private int programId;
//    @JsonProperty("program_classification_ids_allowed")
//    private boolean programClassificationIdsAllowed;
//    @JsonProperty("team_size_range")
//    private String teamSizeRange;
//    private String toxic;
    @JsonProperty("description_source")
    private String descriptionSource;
    private String subdomain;
    @JsonProperty("full_challonge_url")
    private String fullUrl;
    @JsonProperty("live_image_url")
    private String liveImageUrl;
    @JsonProperty("sign_up_url")
    private String signUpUrl;
    @JsonProperty("review_before_finalizing")
    private boolean reviewBeforeFinalizing;
    @JsonProperty("accepting_predictions")
    private boolean acceptingPredictions;
    @JsonProperty("participatns_locked")
    private boolean participantsLocked;
    @JsonProperty("game_name")
    private String gameName;
    @JsonProperty("participants_swappable")
    private boolean participantsSwappable;
    @JsonProperty("team_convertable")
    private boolean teamConvertable;
    @JsonProperty("group_stages_were_started")
    private boolean groupStagesWereStarted;
}
