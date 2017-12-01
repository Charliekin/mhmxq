package com.mhm.xq.ui.auth.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.mhm.xq.MyApp
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
        initVariable()
    }

    private fun initVariable() {
        setTitleBarRightIcon(R.drawable.menu_share_more, View.OnClickListener {
            startActivity(Intent(MyApp.getContext(), RegisteredActivity::class.java))
        })
    }
}