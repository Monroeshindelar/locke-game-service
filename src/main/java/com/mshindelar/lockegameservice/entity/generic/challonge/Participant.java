package com.mshindelar.lockegameservice.entity.generic.challonge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.Date;

@JsonRootName(value = "participant")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Participant {
    private boolean active;
    @JsonProperty("checked_in_at")
    private Date checkedInAt;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("final_rank")
    private int finalRank;
    @JsonProperty("group_id")
    private int groupId;
    private String icon;
    private int id;
    @JsonProperty("invitation_id")
    private int invitationId;
    @JsonProperty("invitation_email")
    private String invitationEmail;
    private String misc;
    private String name;
    @JsonProperty("on_waiting_list")
    private boolean onWaitingList;
    private int seed;
    @JsonProperty("tournament_id")
    private int tournamentId;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("challonge_username")
    private String challongeUsername;
    @JsonProperty("challonge_email_address_verified")
    private boolean challongeEmailAddressVerified;
    private boolean removable;
    @JsonProperty("participatable_or_invitation_attached")
    private boolean participatableOrInvitationAttached;
    @JsonProperty("confirm_remove")
    private boolean confirmRemove;
    @JsonProperty("invitation_pending")
    private boolean invitationPending;
    @JsonProperty("display_name_with_invitation_email_address")
    private String displayNameWithInvitationEmailAddress;
    @JsonProperty("email_hash")
    private String emailHash;
    private String username;
    @JsonProperty("attached_participatable_portrait_url")
    private String attachedParticipatablePortraitUrl;
    @JsonProperty("can_check_in")
    private boolean canCheckIn;
    @JsonProperty("checked_in")
    private boolean checkedIn;
    private boolean reactivatable;
}
