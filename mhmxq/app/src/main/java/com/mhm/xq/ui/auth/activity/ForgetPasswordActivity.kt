package com.mhm.xq.ui.auth.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.entity.User
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.auth.base.activity.BaseAuthActivity
import com.mhm.xq.utils.ToastUtil
import io.reactivex.Observable

class ForgetPasswordActivity : BaseAuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getNetData(): Observable<User>? {
        return MyApi.forgetPassword(getMobile(), getPassword())
    }

    override fun init() {
        setTitleBarTitle(R.string.forget_password, true)
        initVariable()
    }

    private fun initVariable() {
        setButtonTxt(R.string.confirm)
    }

    override fun loadComplete(user: User) {
        super.loadComplete(user)
        ToastUtil.show(this, user.getMessage())
    }

    override fun loadThrowable(throwable: Throwable) {
        super.loadThrowable(throwable)
    }
}