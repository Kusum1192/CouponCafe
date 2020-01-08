package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SliderDatum {

@SerializedName("offerId")
@Expose
private Integer offerId;
@SerializedName("imageUrl")
@Expose
private String imageUrl;

public Integer getOfferId() {
return offerId;
}

public void setOfferId(Integer offerId) {
this.offerId = offerId;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

}