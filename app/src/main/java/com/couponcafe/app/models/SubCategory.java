package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("subCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("subCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("offersCount")
    @Expose
    private String offersCount;

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getOffersCount() {
        return offersCount;
    }

    public void setOffersCount(String offersCount) {
        this.offersCount = offersCount;
    }

}