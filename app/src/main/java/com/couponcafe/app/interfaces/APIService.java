package com.couponcafe.app.interfaces;


import android.net.Uri;

import com.couponcafe.app.models.AllCategoriesDetailsModel;
import com.couponcafe.app.models.AllOffersDataModel;
import com.couponcafe.app.models.BestOfferDetailsModel;
import com.couponcafe.app.models.CategoriesModel;
import com.couponcafe.app.models.CustomNotifyModel;
import com.couponcafe.app.models.InviteFriendModel;
import com.couponcafe.app.models.Notification;
import com.couponcafe.app.models.ProductDetailsModel;
import com.couponcafe.app.models.ProfileDataModel;
import com.couponcafe.app.models.TopStoreDetailsModel;
import com.couponcafe.app.models.UserAppOpenModel;
import com.couponcafe.app.models.UserRegisterModel;
import com.couponcafe.app.models.ViewTopStoreModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {


    @FormUrlEncoded
    @POST("homePage")
    Call<AllOffersDataModel> getOffers(@Field("userId") int userId,
                                       @Field("securityToken") String securityToken,
                                       @Field("versionName") String versionName,
                                       @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("topStoreList")
    Call<ViewTopStoreModel> viewTopStore(@Field("userId") int userId,
                                         @Field("securityToken") String securityToken,
                                         @Field("versionName") String versionName,
                                         @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("bestOfferDetails")
    Call<BestOfferDetailsModel> viewofferDetails(@Field("userId") int userId,
                                                 @Field("securityToken") String securityToken,
                                                 @Field("versionName") String versionName,
                                                 @Field("versionCode") int versionCode,
                                                 @Field("offerId") String offerid);


    @FormUrlEncoded
    @POST("storeDetails")
    Call<TopStoreDetailsModel> getTopStoreDetial(@Field("storeId") String storeId,
                                                 @Field("userId") int userId,
                                                 @Field("securityToken") String securityToken,
                                                 @Field("versionName") String versionName,
                                                 @Field("versionCode") int versionCode);


    @FormUrlEncoded
    @POST("categoryList")
    Call<CategoriesModel> getAllCategories(@Field("userId") int userId,
                                           @Field("securityToken") String securityToken,
                                           @Field("versionName") String versionName,
                                           @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("invitePage")
    Call<InviteFriendModel> getinviteData(@Field("userId") int userId,
                                          @Field("securityToken") String securityToken,
                                          @Field("versionName") String versionName,
                                          @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("userProfile")
    Call<ProfileDataModel> getprofileData(@Field("userId") int userId,
                                          @Field("securityToken") String securityToken,
                                          @Field("versionName") String versionName,
                                          @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("categoryDetails")
    Call<AllCategoriesDetailsModel> allcategoriesDetails(@Field("categoryId") String catId,
                                                         @Field("subCategoryId") String subCategoryId,
                                                         @Field("userId") int userId,
                                                         @Field("securityToken") String securityToken,
                                                         @Field("versionName") String versionName,
                                                         @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("customNotify")
    Call<CustomNotifyModel> getnotificationData(@Field("userId") int userId,
                                                @Field("securityToken") String securityToken,
                                                @Field("versionName") String versionName,
                                                @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("productDetails")
    Call<ProductDetailsModel> getProductData(@Field("userId") int userId,
                                             @Field("securityToken") String securityToken,
                                             @Field("versionName") String versionName,
                                             @Field("versionCode") int versionCode,
                                             @Field("productId") String productId);

    @FormUrlEncoded
    @POST("userAppOpen")
    Call<UserAppOpenModel> appOpen(@Field("userId") int userId,
                                   @Field("securityToken") String securityToken,
                                   @Field("versionName") String versionName,
                                   @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("userRegister")
    Call<UserRegisterModel> userSignUp(@Field("deviceType") String deviceType,
                                       @Field("deviceId") String deviceId,
                                       @Field("deviceName") String deviceName,
                                       @Field("socialType") String socialType,
                                       @Field("socialId") String socialId,
                                       @Field("socialEmail") String socialEmail,
                                       @Field("socialName") String socialName,
                                       @Field("socialImgurl") Uri socialImgurl,
                                       @Field("advertisingId") String advertisingId,
                                       @Field("versionName") String versionName,
                                       @Field("versionCode") int versionCode,
                                       @Field("fcmToken") String token,
                                       @Field("utmSource") String utmSource,
                                       @Field("utmMedium") String utmMedium,
                                       @Field("utmTerm") String utmTerm,
                                       @Field("utmContent") String utmContent,
                                       @Field("utmCampaign") String utmCampaign);
}