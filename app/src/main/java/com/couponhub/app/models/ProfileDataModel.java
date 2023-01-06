package com.couponhub.app.models;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileDataModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("minPayLimit")
    @Expose
    private Integer minPayLimit;
    @SerializedName("userCoin")
    @Expose
    private Integer userCoin;
    @SerializedName("userAmount")
    @Expose
    private Integer userAmount;
    @SerializedName("pendingAmount")
    @Expose
    private Integer pendingAmount;
    @SerializedName("userSavings")
    @Expose
    private Integer userSavings;
    @SerializedName("socialName")
    @Expose
    private String socialName;
    @SerializedName("socialEmail")
    @Expose
    private String socialEmail;
    @SerializedName("socialImgurl")
    @Expose
    private String socialImgurl;
    @SerializedName("products")
    @Expose
    private ArrayList<Product> products = null;

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

    public Integer getMinPayLimit() {
        return minPayLimit;
    }

    public void setMinPayLimit(Integer minPayLimit) {
        this.minPayLimit = minPayLimit;
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

    public Integer getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(Integer pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Integer getUserSavings() {
        return userSavings;
    }

    public void setUserSavings(Integer userSavings) {
        this.userSavings = userSavings;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getSocialEmail() {
        return socialEmail;
    }

    public void setSocialEmail(String socialEmail) {
        this.socialEmail = socialEmail;
    }

    public String getSocialImgurl() {
        return socialImgurl;
    }

    public void setSocialImgurl(String socialImgurl) {
        this.socialImgurl = socialImgurl;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}