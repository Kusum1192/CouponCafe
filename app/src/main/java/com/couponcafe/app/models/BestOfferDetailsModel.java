package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BestOfferDetailsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("offerDetails")
@Expose
private OfferDetails offerDetails;

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

public OfferDetails getOfferDetails() {
return offerDetails;
}

public void setOfferDetails(OfferDetails offerDetails) {
this.offerDetails = offerDetails;
}

}