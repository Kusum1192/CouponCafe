package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailsModel {

@SerializedName("message")
@Expose
private String message;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("productDetails")
@Expose
private ProductDetails productDetails;

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

public ProductDetails getProductDetails() {
return productDetails;
}

public void setProductDetails(ProductDetails productDetails) {
this.productDetails = productDetails;
}

}