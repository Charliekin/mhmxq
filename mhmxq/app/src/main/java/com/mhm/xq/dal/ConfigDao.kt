package com.mhm.xq.dal

import com.mhm.xq.entity.vo.Config
import com.mhm.xq.utils.PreferencesUtil

class ConfigDao {
    companion object {
        private val PREV_VERSION_CODE = "prevVersionCode"
        private val IS_AUTO_LOGIN_KEY = "isAutoLogin"
        private val CURRENT_LOGIN_UID = "currentLoginUid"
        private val FIRST_RUN = "firstRun"
    }

    fun loadSingle(): Config {
        val config = Config()
        config.setPrevVersionCode(PreferencesUtil.getInt(PREV_VERSION_CODE, -1))
        config.setIsAutoLogin(PreferencesUtil.getBoolean(IS_AUTO_LOGIN_KEY, false))
        config.setCurrentLoginId(PreferencesUtil.getString(CURRENT_LOGIN_UID))
        config.setFirstRun(PreferencesUtil.getBoolean(FIRST_RUN, false))
        return config
    }

    fun saveSingle(config: Config): Config {
        PreferencesUtil.putBoolean(IS_AUTO_LOGIN_KEY, config.getIsAutoLogin())
        PreferencesUtil.putString(CURRENT_LOGIN_UID, config.getCurrentLoginId()!!)
        PreferencesUtil.putInt(PREV_VERSION_CODE, config.getPrevVersionCode())
        PreferencesUtil.putBoolean(FIRST_RUN, config.isFirstRun())
        return config
    }

}