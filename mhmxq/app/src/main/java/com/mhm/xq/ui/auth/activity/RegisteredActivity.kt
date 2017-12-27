package com.mhm.xq.ui.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.entity.greendao.User
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.auth.base.activity.BaseAuthActivity
import com.mhm.xq.utils.ToastUtil
import io.reactivex.Observable

class RegisteredActivity : BaseAuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getNetData(): Observable<User>? {
        return MyApi.registration(getMobile(), getPassword())
    }

    override fun init() {
        setTitleBarTitle(R.string.registration, true)
        initVariable()
    }

    private fun initVariable() {
        setButtonTxt(R.string.registration)
        setTitleBarRightIcon(R.drawable.menu_share_more, View.OnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        })
    }

    override fun loadComplete(user: User) {
        super.loadComplete(user)
        ToastUtil.show(this, user.getMessage())
    }

    override fun loadThrowable(throwable: Throwable) {
        super.loadThrowable(throwable)
    }
}