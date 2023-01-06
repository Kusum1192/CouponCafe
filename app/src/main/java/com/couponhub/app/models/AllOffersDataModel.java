package com.couponhub.app.models;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllOffersDataModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("userCoin")
    @Expose
    private Integer userCoin;
    @SerializedName("userAmount")
    @Expose
    private Integer userAmount;
    @SerializedName("inviteImgurl")
    @Expose
    private String inviteImgurl;
    @SerializedName("sliderData")
    @Expose
    private ArrayList<SliderDatum> sliderData = null;
    @SerializedName("topStoreData")
    @Expose
    private ArrayList<TopStoreDatum> topStoreData = null;
    @SerializedName("bestOfferData")
    @Expose
    private ArrayList<BestOfferDatum> bestOfferData = null;

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

    public Integer getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(Integer userCoin) {
        this.userCoin = userCoin;
    }

    public Integer getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Integer userAmount) {
        this.userAmount = userAmount;
    }

    public String getInviteImgurl() {
        return inviteImgurl;
    }

    public void setInviteImgurl(String inviteImgurl) {
        this.inviteImgurl = inviteImgurl;
    }

    public ArrayList<SliderDatum> getSliderData() {
        return sliderData;
    }

    public void setSliderData(ArrayList<SliderDatum> sliderData) {
        this.sliderData = sliderData;
    }

    public ArrayList<TopStoreDatum> getTopStoreData() {
        return topStoreData;
    }

    public void setTopStoreData(ArrayList<TopStoreDatum> topStoreData) {
        this.topStoreData = topStoreData;
    }

    public ArrayList<BestOfferDatum> getBestOfferData() {
        return bestOfferData;
    }

    public void setBestOfferData(ArrayList<BestOfferDatum> bestOfferData) {
        this.bestOfferData = bestOfferData;
    }

}