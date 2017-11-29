package com.mhm.xq.ui.auth.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class SignActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_sign)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.login, true)
    }


}