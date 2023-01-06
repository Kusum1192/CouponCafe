package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponDealData {

@SerializedName("showText")
@Expose
private String showText;
@SerializedName("value")
@Expose
private String value;

public String getShowText() {
return showText;
}

public void setShowText(String showText) {
this.showText = showText;
}

public String getValue() {
return value;
}

public void setValue(String value) {
this.value = value;
}

}