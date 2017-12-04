package com.mhm.xq.ui.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mhm.xq.R
import com.mhm.xq.entity.User
import com.mhm.xq.net.http.rest.MyApi
import com.mhm.xq.ui.auth.base.activity.BaseAuthActivity
import com.mhm.xq.utils.StringUtil
import com.mhm.xq.utils.ToastUtil
import io.reactivex.Observable

class SignActivity : BaseAuthActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getNetData(): Observable<User>? {
        if (StringUtil.isBlank(getMobile())) {
            ToastUtil.show(this, R.string.mobile_hint)
            return null
        }
        if (StringUtil.isBlank(getPassword())) {
            ToastUtil.show(this, R.string.password_hint)
            return null
        }
        return MyApi.login(getMobile(), getPassword())
    }

    override fun init() {
        setTitleBarTitle(R.string.login, true)
        initVariable()
    }

    private fun initVariable() {
        setButtonTxt(R.string.login)
        setTitleBarRightIcon(R.drawable.menu_share_more, View.OnClickListener {
            startActivity(Intent(this, RegisteredActivity::class.java))
        })
    }

    override fun loadComplete(user: User) {
        super.loadComplete(user)
    }

    override fun loadThrowable(throwable: Throwable) {
        super.loadThrowable(throwable)
    }

}