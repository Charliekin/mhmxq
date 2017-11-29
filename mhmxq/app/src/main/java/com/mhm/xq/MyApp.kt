package com.mhm.xq

import android.app.Application
import android.content.Context
import com.mhm.xq.utils.AppUtil
import com.mhm.xq.utils.PreferencesUtil

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext
        val processName = AppUtil.getCurrentProcessName(applicationContext)
        if (processName != null && processName == packageName) {
            PreferencesUtil.init(applicationContext)
        }
    }

    companion object {
        private var sContext: Context? = null
        public fun getContext(): Context? {
            return sContext
        }
    }
}