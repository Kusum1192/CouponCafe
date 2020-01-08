package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopOfferDatum {

@SerializedName("offerId")
@Expose
private Integer offerId;
@SerializedName("offerName")
@Expose
private String offerName;
@SerializedName("cashBack")
@Expose
private String cashBack;
@SerializedName("imageUrl")
@Expose
private String imageUrl;

public Integer getOfferId() {
return offerId;
}

public void setOfferId(Integer offerId) {
this.offerId = offerId;
}

public String getOfferName() {
return offerName;
}

public void setOfferName(String offerName) {
this.offerName = offerName;
}

public String getCashBack() {
return cashBack;
}

public void setCashBack(String cashBack) {
this.cashBack = cashBack;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

}