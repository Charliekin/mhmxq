package com.mhm.xq.ui.me.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class MyAttention : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_attention)
        init()
    }

    private fun init() {
        setTitleBarTitleLeft(R.string.me_attention, true)
    }


}