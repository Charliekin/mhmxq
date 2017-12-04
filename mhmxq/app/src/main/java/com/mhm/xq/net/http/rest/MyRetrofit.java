package com.mhm.xq.net.http.rest;

import com.mhm.xq.BuildConfig;
import com.mhm.xq.gson.MyGson;
import com.mhm.xq.net.http.client.GsonConverterFactory;
import com.mhm.xq.net.http.client.OkHttpClientManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MyRetrofit {

    private static volatile MyRetrofit sInstance;
    private IMyApi mApi;

    public IMyApi getApi() {
        return mApi;
    }

    public static MyRetrofit getInstance() {
        if (sInstance == null) {
            synchronized (MyRetrofit.class) {
                if (sInstance == null) {
                    sInstance = new MyRetrofit();
                }
            }
        }
        return sInstance;
    }

    private MyRetrofit() {
        OkHttpClient client = OkHttpClientManager.Companion.getInstance().getOkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.HTTP_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(MyGson.Companion.build()))
                .client(client)
                .build();
        mApi = retrofit.create(IMyApi.class);
    }
}
