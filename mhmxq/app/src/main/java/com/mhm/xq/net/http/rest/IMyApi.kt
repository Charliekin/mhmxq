package com.mhm.xq.net.http.rest

import com.mhm.xq.entity.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IMyApi {

    @FormUrlEncoded
    @POST("/login")
    fun login(): Observable<User>

    @FormUrlEncoded
    @POST("/registration")
    fun registration(@Field("mobile") mobile: String,
                     @Field("password") password: String): Observable<User>

}