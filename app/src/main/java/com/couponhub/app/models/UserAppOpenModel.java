package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAppOpenModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("forceUpdate")
@Expose
private boolean forceUpdate;
@SerializedName("currency")
@Expose
private String currency;
@SerializedName("userCoin")
@Expose
private Integer userCoin;
@SerializedName("userAmount")
@Expose
private Integer userAmount;
@SerializedName("packAge")
@Expose
private String packAge;

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

public boolean getForceUpdate() {
return forceUpdate;
}

public void setForceUpdate(boolean forceUpdate) {
this.forceUpdate = forceUpdate;
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

public String getPackAge() {
return packAge;
}

public void setPackAge(String packAge) {
this.packAge = packAge;
}

}