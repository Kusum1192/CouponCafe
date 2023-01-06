package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

@SerializedName("offerId")
@Expose
private Integer offerId;
@SerializedName("timesAgo")
@Expose
private String timesAgo;
@SerializedName("notifyTitle")
@Expose
private String notifyTitle;
@SerializedName("imageUrl")
@Expose
private String imageUrl;
@SerializedName("shortDescription")
@Expose
private String shortDescription;

public Integer getOfferId() {
return offerId;
}

public void setOfferId(Integer offerId) {
this.offerId = offerId;
}

public String getTimesAgo() {
return timesAgo;
}

public void setTimesAgo(String timesAgo) {
this.timesAgo = timesAgo;
}

public String getNotifyTitle() {
return notifyTitle;
}

public void setNotifyTitle(String notifyTitle) {
this.notifyTitle = notifyTitle;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getShortDescription() {
return shortDescription;
}

public void setShortDescription(String shortDescription) {
this.shortDescription = shortDescription;
}

}