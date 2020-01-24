package com.couponcafe.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

@SerializedName("productId")
@Expose
private Integer productId;
@SerializedName("productName")
@Expose
private String productName;
@SerializedName("productPrice")
@Expose
private String productPrice;
@SerializedName("imageUrl")
@Expose
private String imageUrl;
@SerializedName("shortDescription")
@Expose
private String shortDescription;

public Integer getProductId() {
return productId;
}

public void setProductId(Integer productId) {
this.productId = productId;
}

public String getProductName() {
return productName;
}

public void setProductName(String productName) {
this.productName = productName;
}

public String getProductPrice() {
return productPrice;
}

public void setProductPrice(String productPrice) {
this.productPrice = productPrice;
}

public String getImageUrl() {
return imageUrl;
}

public void setImageUrl(String imageUrl) {
this.imageUrl = imageUrl;
}

public String getShortDescription() {
return shortDescription;
}

public void setShortDescription(String shortDescription) {
this.shortDescription = shortDescription;
}

}