package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomNotifyModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("notifications")
@Expose
private ArrayList<Notification> notifications = null;

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

public ArrayList<Notification> getNotifications() {
return notifications;
}

public void setNotifications(ArrayList<Notification> notifications) {
this.notifications = notifications;
}

}