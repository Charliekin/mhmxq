package com.mhm.xq.net.http.client

import com.mhm.xq.BuildConfig
import com.mhm.xq.MyApp
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

class OkHttpClientManager {


    companion object {
        private var mOkHttpClient: OkHttpClient? = null
        private var sInstance: OkHttpClientManager? = null
        private var mCookieStore: PersistentCookieStore? = null

        public fun getInstance(): OkHttpClientManager? {
            if (sInstance == null) {
                synchronized(this) {
                    if (sInstance == null) {
                        sInstance = OkHttpClientManager()
                    }
                }
            }
            return sInstance
        }
    }

    public fun getOkHttpClient(): OkHttpClient? {
        return mOkHttpClient
    }

    private constructor() {
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        mCookieStore = PersistentCookieStore(MyApp.getContext()!!)
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.readTimeout(100, TimeUnit.SECONDS)
        builder.cookieJar(JavaNetCookieJar(CookieManager(mCookieStore, CookiePolicy.ACCEPT_ALL)))
        builder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain?): Response {
                var request: Request = chain!!.request()
                request = request.newBuilder()
                        .addHeader("X_Platrorm", "1")
                        .build()
                return chain.proceed(request)
            }

        })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }
        mOkHttpClient = builder.build()
    }

}