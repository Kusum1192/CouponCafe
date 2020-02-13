package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferDetails {

    @SerializedName("offerId")
    @Expose
    private Integer offerId;
    @SerializedName("offerName")
    @Expose
    private String offerName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cashBack")
    @Expose
    private String cashBack;
    @SerializedName("shopVia")
    @Expose
    private Integer shopVia;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("couponDealData")
    @Expose
    private CouponDealData couponDealData;
    @SerializedName("recentUser")
    @Expose
    private ArrayList<RecentUser> recentUser = null;

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

    public String getCashBack() {
        return cashBack;
    }

    public void setCashBack(String cashBack) {
        this.cashBack = cashBack;
    }

    public Integer getShopVia() {
        return shopVia;
    }

    public void setShopVia(Integer shopVia) {
        this.shopVia = shopVia;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CouponDealData getCouponDealData() {
        return couponDealData;
    }

    public void setCouponDealData(CouponDealData couponDealData) {
        this.couponDealData = couponDealData;
    }

    public ArrayList<RecentUser> getRecentUser() {
        return recentUser;
    }

    public void setRecentUser(ArrayList<RecentUser> recentUser) {
        this.recentUser = recentUser;
    }
}