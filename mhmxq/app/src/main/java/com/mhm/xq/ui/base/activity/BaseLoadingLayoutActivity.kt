package com.mhm.xq.ui.base.activity

import android.widget.FrameLayout
import com.mhm.xq.R
import com.mhm.xq.ui.base.widget.LoadingLayout

open class BaseLoadingLayoutActivity : BaseActivity() {


    private var loadingLayout: LoadingLayout? = null

    fun getLoadingLayout(): LoadingLayout {
        if (loadingLayout == null) {
            loadingLayout = findViewById<FrameLayout>(R.id.loadingLayoutView) as LoadingLayout
        }

        return loadingLayout!!
    }

    enum class LoadType {
        firstPage,
        olderPage
    }
}