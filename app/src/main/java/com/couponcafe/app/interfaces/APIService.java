package com.couponcafe.app.interfaces;


import com.couponcafe.app.models.AllCategoriesDetailsModel;
import com.couponcafe.app.models.AllOffersDataModel;
import com.couponcafe.app.models.BestOfferDetailsModel;
import com.couponcafe.app.models.CategoriesModel;
import com.couponcafe.app.models.InviteFriendModel;
import com.couponcafe.app.models.ProfileDataModel;
import com.couponcafe.app.models.TopStoreDetailsModel;
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
    Call<BestOfferDetailsModel> viewofferDetails(@Field("offerId") String offerid,
                                                 @Field("userId") int userId,
                                                 @Field("securityToken") String securityToken,
                                                 @Field("versionName") String versionName,
                                                 @Field("versionCode") int versionCode);


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
    Call<AllCategoriesDetailsModel> allcategoriesDetails(@Field("userId") int userId,
                                                         @Field("securityToken") String securityToken,
                                                         @Field("versionName") String versionName,
                                                         @Field("versionCode") int versionCode);
}