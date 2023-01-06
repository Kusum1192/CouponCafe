package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentUser {

@SerializedName("userName")
@Expose
private String userName;
@SerializedName("showText")
@Expose
private String showText;
@SerializedName("date")
@Expose
private String date;
@SerializedName("imageUrl")
@Expose
private String imageUrl;

public String getUserName() {
return userName;
}

public void setUserName(String userName) {
this.userName = userName;
}

public String getShowText() {
return showText;
}

public void setShowText(String showText) {
this.showText = showText;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

}