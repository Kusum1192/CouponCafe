package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferClickedModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("actionType")
@Expose
private String actionType;
@SerializedName("actionUrl")
@Expose
private String actionUrl;

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

public String getActionType() {
return actionType;
}

public void setActionType(String actionType) {
this.actionType = actionType;
}

public String getActionUrl() {
return actionUrl;
}

public void setActionUrl(String actionUrl) {
this.actionUrl = actionUrl;
}

}