package com.mhm.xq.net.http.rest

import com.mhm.xq.BuildConfig
import com.mhm.xq.net.http.client.OkHttpClientManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class MyRetrofit {

    private var mApi: IMyApi? = null

    public fun getApi(): IMyApi? {
        return mApi
    }

    companion object {
        private var sInstance: MyRetrofit? = null

        public fun getInstance(): MyRetrofit? {
            if (sInstance == null) {
                synchronized(MyRetrofit::class) {
                    if (sInstance == null) {
                        sInstance = MyRetrofit()
                    }
                }
            }
            return sInstance
        }
    }


    private constructor() {
        val client: OkHttpClient = OkHttpClientManager.getInstance()!!.getOkHttpClient()!!
        val retrofit: Retrofit? = Retrofit.Builder()
                .baseUrl(BuildConfig.HTTP_HOST)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(MyGson.build()))
//                .client(client)
                .build()
        mApi = retrofit!!.create(IMyApi::class.java)
    }

}