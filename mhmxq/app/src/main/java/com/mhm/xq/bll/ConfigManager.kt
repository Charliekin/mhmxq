package com.mhm.xq.bll

import com.mhm.xq.MyApp
import com.mhm.xq.dal.ConfigDao
import com.mhm.xq.entity.vo.Config
import com.mhm.xq.utils.AppUtil

class ConfigManager {
    private var mConfigDao: ConfigDao? = null

    constructor() {
        mConfigDao = ConfigDao()
    }

    companion object {
        @Volatile private var sInstance: ConfigManager? = null
        private var sConfig: Config? = null

        fun getInstance(): ConfigManager? {
            if (sInstance == null) {
                synchronized(ConfigManager.javaClass) {
                    if (sInstance == null) {
                        sInstance = ConfigManager()
                    }
                }
            }
            return sInstance
        }
    }

    fun init(): Config {
        val config = loadSingle()
        val newVersionCode = AppUtil.getAppVersionCode(MyApp.getContext()!!)
        val firstRun = config.getPrevVersionCode() < newVersionCode
        config.setFirstRun(firstRun)
        return saveSingle(config)
    }

    fun loadSingle(): Config {
        if (sConfig == null) {
            sConfig = mConfigDao!!.loadSingle()
        }
        return sConfig as Config
    }

    fun saveSingle(config: Config): Config {
        mConfigDao!!.saveSingle(config)
        sConfig = config
        return config
    }

    fun isFirstRun(): Boolean {
        return loadSingle().isFirstRun()
    }

    fun updateVersionCode() {
        var newVersionCode: Int = AppUtil.getAppVersionCode(MyApp.getContext()!!)
        var confit: Config = loadSingle()
        confit.setPrevVersionCode(newVersionCode)
        saveSingle(confit)
    }

    fun checkIsAuthLogin(): Boolean {
        return loadSingle().getIsAutoLogin()
    }

}