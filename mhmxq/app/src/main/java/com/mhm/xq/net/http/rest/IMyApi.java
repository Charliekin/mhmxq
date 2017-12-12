package com.mhm.xq.net.http.rest;

import com.mhm.xq.entity.User;
import com.mhm.xq.entity.base.BaseEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface IMyApi {

    //<!--  editor-fold  desc="user validation"  -->

    @FormUrlEncoded
    @POST("/registration")
    Observable<User> registration(@Field("mobile") String mobile,
                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("/login")
    Observable<User> login(@Field("mobile") String mobile,
                           @Field("password") String password);

    @FormUrlEncoded
    @POST("/forgetpassword")
    Observable<User> forgetPassword(@Field("mobile") String mobile,
                                    @Field("password") String password);

    @Multipart
    @POST("/fileUpload")
    Observable<BaseEntity> uploadUserIcon(@Part MultipartBody.Part icon);

    @GET("fileUpload")
    Observable<ResponseBody> downloadUserIcon();

    //<!--  editor-fold  -->

    //<!--  editor-fold  desc="HomeFragment"  -->

    @GET("/channel")
    Observable<BaseEntity> getChannel();

    //<!--  editor-fold  -->
}
