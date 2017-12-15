package com.mhm.xq.ui.common.activity

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class GeneralViewPictureActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_general_view_pic)
        init()
    }

    private fun init() {
        setTitleBarTitle("", true)
    }
}