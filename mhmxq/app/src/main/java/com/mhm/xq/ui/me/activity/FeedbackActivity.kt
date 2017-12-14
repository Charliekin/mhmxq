package com.mhm.xq.ui.me.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class FeedbackActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_feedback)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.me_feedback, true)
    }
}