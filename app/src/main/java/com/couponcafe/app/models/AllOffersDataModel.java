package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
@SerializedName("sliderData")
@Expose
private ArrayList<SliderDatum> sliderData = null;
@SerializedName("topOfferData")
@Expose
private ArrayList<TopOfferDatum> topOfferData = null;
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

public ArrayList<SliderDatum> getSliderData() {
return sliderData;
}

public void setSliderData(ArrayList<SliderDatum> sliderData) {
this.sliderData = sliderData;
}

public ArrayList<TopOfferDatum> getTopOfferData() {
return topOfferData;
}

public void setTopOfferData(ArrayList<TopOfferDatum> topOfferData) {
this.topOfferData = topOfferData;
}

public ArrayList<BestOfferDatum> getBestOfferData() {
return bestOfferData;
}

public void setBestOfferData(ArrayList<BestOfferDatum> bestOfferData) {
this.bestOfferData = bestOfferData;
}

}
