package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteFriendModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("inviteFbUrl")
    @Expose
    private String inviteFbUrl;
    @SerializedName("inviteTextUrl")
    @Expose
    private String inviteTextUrl;
    @SerializedName("inviteImgurl")
    @Expose
    private String inviteImgurl;
    @SerializedName("inviteText")
    @Expose
    private String inviteText;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInviteFbUrl() {
        return inviteFbUrl;
    }

    public void setInviteFbUrl(String inviteFbUrl) {
        this.inviteFbUrl = inviteFbUrl;
    }

    public String getInviteTextUrl() {
        return inviteTextUrl;
    }

    public void setInviteTextUrl(String inviteTextUrl) {
        this.inviteTextUrl = inviteTextUrl;
    }

    public String getInviteImgurl() {
        return inviteImgurl;
    }

    public void setInviteImgurl(String inviteImgurl) {
        this.inviteImgurl = inviteImgurl;
    }

    public String getInviteText() {
        return inviteText;
    }

    public void setInviteText(String inviteText) {
        this.inviteText = inviteText;
    }

}