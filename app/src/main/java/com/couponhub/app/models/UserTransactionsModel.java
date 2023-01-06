package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserTransactionsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("transactions")
@Expose
private ArrayList<Transaction> transactions = null;

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

public ArrayList<Transaction> getTransactions() {
return transactions;
}

public void setTransactions(ArrayList<Transaction> transactions) {
this.transactions = transactions;
}

}