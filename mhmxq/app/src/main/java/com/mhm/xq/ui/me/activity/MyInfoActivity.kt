package com.mhm.xq.ui.me.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class MyInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_info)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.my_info_title, true)
    }
}