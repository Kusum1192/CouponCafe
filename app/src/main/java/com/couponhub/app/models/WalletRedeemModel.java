package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletRedeemModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("minPayLimit")
@Expose
private Integer minPayLimit;
@SerializedName("userCoin")
@Expose
private Integer userCoin;
@SerializedName("userAmount")
@Expose
private Integer userAmount;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("showText")
@Expose
private String showText;

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

public String getCurrency() {
return currency;
}

public void setCurrency(String currency) {
this.currency = currency;
}

public String getShowText() {
return showText;
}

public void setShowText(String showText) {
this.showText = showText;
}

}