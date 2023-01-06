package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStoreDetailsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("storeDetails")
@Expose
private StoreDetails storeDetails;

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

public StoreDetails getStoreDetails() {
return storeDetails;
}

public void setStoreDetails(StoreDetails storeDetails) {
this.storeDetails = storeDetails;
}

}