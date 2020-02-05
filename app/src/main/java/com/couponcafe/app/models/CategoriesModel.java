package com.couponcafe.app.models;

import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("categoryData")
    @Expose
    private ArrayList<CategoryDatum> categoryData = null;

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

    public ArrayList<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(ArrayList<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }


}