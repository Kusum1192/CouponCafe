package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStoreDatum {

    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cashBack")
    @Expose
    private String cashBack;
    @SerializedName("offersCount")
    @Expose
    private String offersCount;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(String offersCount) {
        this.offersCount = offersCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}