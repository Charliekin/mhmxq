package com.mhm.xq.entity.vo

class Config {

    private var prevVersionCode: Int = 0
    private var isAutoLogin: Boolean = false
    private var currentLoginId: String? = null
    private var firstRun: Boolean = false


    fun getPrevVersionCode(): Int {
        return prevVersionCode
    }

    fun setPrevVersionCode(prevVersionCode: Int) {
        this.prevVersionCode = prevVersionCode
    }

    fun getIsAutoLogin(): Boolean {
        return isAutoLogin
    }

    fun setIsAutoLogin(isAutoLogin: Boolean) {
        this.isAutoLogin = isAutoLogin
    }

    fun getCurrentLoginId(): String? {
        return currentLoginId
    }

    fun setCurrentLoginId(currentLoginId: String) {
        this.currentLoginId = currentLoginId
    }

    fun isFirstRun(): Boolean {
        return firstRun
    }

    fun setFirstRun(firstRun: Boolean) {
        this.firstRun = firstRun
    }
}