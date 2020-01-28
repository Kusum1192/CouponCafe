package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegisterModel {

@SerializedName("status")
@Expose
private Integer status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("userId")
@Expose
private Integer userId;
@SerializedName("securityToken")
@Expose
private String securityToken;
@SerializedName("socialName")
@Expose
private String socialName;
@SerializedName("socialImgurl")
@Expose
private String socialImgurl;

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public String getSecurityToken() {
return securityToken;
}

public void setSecurityToken(String securityToken) {
this.securityToken = securityToken;
}

public String getSocialName() {
return socialName;
}

public void setSocialName(String socialName) {
this.socialName = socialName;
}

public String getSocialImgurl() {
return socialImgurl;
}

public void setSocialImgurl(String socialImgurl) {
this.socialImgurl = socialImgurl;
}

}