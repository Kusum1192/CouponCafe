package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("transName")
@Expose
private String transName;
@SerializedName("transDate")
@Expose
private String transDate;
@SerializedName("transTime")
@Expose
private String transTime;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getTransName() {
return transName;
}

public void setTransName(String transName) {
this.transName = transName;
}

public String getTransDate() {
return transDate;
}

public void setTransDate(String transDate) {
this.transDate = transDate;
}

public String getTransTime() {
return transTime;
}

public void setTransTime(String transTime) {
this.transTime = transTime;
}

}