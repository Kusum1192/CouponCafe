package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllCategoriesDetailsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("subCategories")
@Expose
private ArrayList<SubCategory> subCategories = null;
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

public ArrayList<SubCategory> getSubCategories() {
return subCategories;
}

public void setSubCategories(ArrayList<SubCategory> subCategories) {
this.subCategories = subCategories;
}

public ArrayList<BestOfferDatum> getBestOfferData() {
return bestOfferData;
}

public void setBestOfferData(ArrayList<BestOfferDatum> bestOfferData) {
this.bestOfferData = bestOfferData;
}

}