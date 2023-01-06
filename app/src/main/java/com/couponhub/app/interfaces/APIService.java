package com.couponhub.app.interfaces;


import android.net.Uri;

import com.couponhub.app.models.AllCategoriesDetailsModel;
import com.couponhub.app.models.AllOffersDataModel;
import com.couponhub.app.models.BestOfferDetailsModel;
import com.couponhub.app.models.CategoriesModel;
import com.couponhub.app.models.CustomNotifyModel;
import com.couponhub.app.models.InviteFriendModel;
import com.couponhub.app.models.OfferClickedModel;
import com.couponhub.app.models.PayoutDataModel;
import com.couponhub.app.models.ProductDetailsModel;
import com.couponhub.app.models.ProfileDataModel;
import com.couponhub.app.models.TopStoreDetailsModel;
import com.couponhub.app.models.UserAppOpenModel;
import com.couponhub.app.models.UserRegisterModel;
import com.couponhub.app.models.UserSignIN;
import com.couponhub.app.models.UserTransactionsModel;
import com.couponhub.app.models.ViewTopStoreModel;
import com.couponhub.app.models.WalletRedeemModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("userSignIn")
    Call<UserSignIN> userSignIn(@Field("email") String email,
                                @Field("password") String password,
                                @Field("versionName") String versionName,
                                @Field("versionCode") int versionCode);
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

    @FormUrlEncoded
    @POST("payoutData")
    Call<PayoutDataModel>getpayoutData(@Field("userId") int userId,
                                       @Field("securityToken") String securityToken,
                                       @Field("versionName") String versionName,
                                       @Field("versionCode") int versionCode);
    @FormUrlEncoded
    @POST("walletRedeem")
    Call<WalletRedeemModel> walletDataRedeem(@Field("userId") int userId,
                                             @Field("securityToken") String securityToken,
                                             @Field("paytmNumber") String paytmNumber,
                                             @Field("payAmount") String payoutAmount,
                                             @Field("payEmail") String payoutEmail,
                                             @Field("redeemType") String paymode,
                                             @Field("versionName") String versionName,
                                             @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("userTransactions")
    Call<UserTransactionsModel> getUserTransaction(@Field("userId") int userId,
                                                   @Field("securityToken") String securityToken,
                                                   @Field("versionName") String versionName,
                                                   @Field("versionCode") int versionCode);

    @FormUrlEncoded
    @POST("bestOfferClicked")
    Call<OfferClickedModel> offerclicked(@Field("userId") int userId,
                                         @Field("securityToken") String securityToken,
                                         @Field("versionName") String versionName,
                                         @Field("versionCode") int versionCode,
                                         @Field("offerId") String offerid);



}