package com.mhm.xq.net.http.rest

import com.mhm.xq.entity.User
import io.reactivex.Observable

class MyApi {

    companion object {
        fun login(mobile: String, password: String): Observable<User> {
            return MyRetrofit.getInstance()!!.getApi()!!.login(/*mobile, password*/)
        }
    }
}