package com.mhm.xq.net.http.rest;

import com.mhm.xq.entity.User;
import com.mhm.xq.entity.base.BaseEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

public class MyApi {

    //<!--  editor-fold  desc="user validation"  -->

    public static Observable<User> registration(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().registration(mobile, password);
    }

    public static Observable<User> login(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().login(mobile, password);
    }

    public static Observable<User> forgetPassword(String mobile, String password) {
        return MyRetrofit.getInstance().getApi().forgetPassword(mobile, password);
    }

    public static Observable<BaseEntity> uploadUserIcon(MultipartBody.Part icon) {
        return MyRetrofit.getInstance().getApi().uploadUserIcon(icon);
    }

    public static Observable<ResponseBody> downloadUserIcon() {
        return MyRetrofit.getInstance().getApi().downloadUserIcon();
    }

    //<!--  editor-fold  -->

    public static Observable<BaseEntity> getChannel() {
        return MyRetrofit.getInstance().getApi().getChannel();
    }
}
