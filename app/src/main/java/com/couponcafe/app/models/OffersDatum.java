package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffersDatum {

    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("offerName")
    @Expose
    private String offerName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("cashBack")
    @Expose
    private String cashBack;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("usrs")
    @Expose
    private Integer usrs;
    @SerializedName("suces")
    @Expose
    private Integer suces;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getUsrs() {
        return usrs;
    }

    public void setUsrs(Integer usrs) {
        this.usrs = usrs;
    }

    public Integer getSuces() {
        return suces;
    }

    public void setSuces(Integer suces) {
        this.suces = suces;
    }
}