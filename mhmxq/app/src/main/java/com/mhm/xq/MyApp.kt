package com.mhm.xq

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.mhm.xq.bll.ConfigManager
import com.mhm.xq.dal.Db
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
        Stetho.initializeWithDefaults(this)
        initStorage()
    }

    companion object {
        private var sContext: Context? = null
        public fun getContext(): Context? {
            return sContext
        }
    }

    private fun initStorage() {
        if (ConfigManager.getInstance()!!.checkIsAuthLogin()) {
            Db.open(ConfigManager.getInstance()!!.getCurrentLoginId() + ".db")
        } else {
            Db.open(Db.UNKNOWN_DB_NAME)
        }
    }
}