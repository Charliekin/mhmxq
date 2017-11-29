package com.mhm.xq.ui.general

import android.os.Bundle
import com.mhm.xq.R
import com.mhm.xq.ui.base.activity.BaseActivity

class SearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_activity_search)
        init()
    }

    private fun init() {
        setTitleBarTitle(R.string.search_title, true)
    }
}