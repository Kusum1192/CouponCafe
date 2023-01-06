package com.couponhub.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductDetails {

@SerializedName("productId")
@Expose
private Integer productId;
@SerializedName("rating")
@Expose
private Double rating;
@SerializedName("ratingUsers")
@Expose
private String ratingUsers;
@SerializedName("productName")
@Expose
private String productName;
@SerializedName("productPrice")
@Expose
private String productPrice;
@SerializedName("discountedPrice")
@Expose
private String discountedPrice;
@SerializedName("shortDescription")
@Expose
private String shortDescription;
@SerializedName("specifications")
@Expose
private String specifications;
@SerializedName("sliderData")
@Expose
private ArrayList<SliderDatum> sliderData = null;

public Integer getProductId() {
return productId;
}

public void setProductId(Integer productId) {
this.productId = productId;
}

public Double getRating() {
return rating;
}

public void setRating(Double rating) {
this.rating = rating;
}

public String getRatingUsers() {
return ratingUsers;
}

public void setRatingUsers(String ratingUsers) {
this.ratingUsers = ratingUsers;
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

public String getDiscountedPrice() {
return discountedPrice;
}

public void setDiscountedPrice(String discountedPrice) {
this.discountedPrice = discountedPrice;
}

public String getShortDescription() {
return shortDescription;
}

public void setShortDescription(String shortDescription) {
this.shortDescription = shortDescription;
}

public String getSpecifications() {
return specifications;
}

public void setSpecifications(String specifications) {
this.specifications = specifications;
}

public ArrayList<SliderDatum> getSliderData() {
return sliderData;
}

public void setSliderData(ArrayList<SliderDatum> sliderData) {
this.sliderData = sliderData;
}

}