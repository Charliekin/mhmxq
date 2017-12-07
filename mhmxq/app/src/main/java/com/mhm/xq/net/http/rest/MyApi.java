package com.mhm.xq.net.http.rest;

import com.mhm.xq.entity.User;
import com.mhm.xq.entity.base.BaseEntity;

import io.reactivex.Observable;

public class MyApi {

    //    <!--  editor-fold  desc="user validation"  -->

    public static Observable<User> registration(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().registration(mobile, password);
    }

    public static Observable<User> login(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().login(mobile, password);
    }

    public static Observable<User> forgetPassword(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().forgetPassword(mobile, password);
    }

    //    <!--  editor-fold  -->

    public static Observable<BaseEntity> getChannel() {
        return MyRetrofit.getInstance().getApi().getChannel();
    }
}
