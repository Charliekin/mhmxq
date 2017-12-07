package com.mhm.xq.net.http.rest;

import com.mhm.xq.entity.User;
import com.mhm.xq.entity.base.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IMyApi {

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

    @GET("/channel")
    Observable<BaseEntity> getChannel();

}
