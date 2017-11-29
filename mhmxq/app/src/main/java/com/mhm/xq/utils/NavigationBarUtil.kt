package com.mhm.xq.utils

import android.content.Context
import android.content.res.Resources

class NavigationBarUtil {

    private var mContext: Context? = null

    constructor(context: Context) {
        mContext = context
    }

    /**
     * 是否存在虚拟键盘
     */
    fun checkDeviceHasNavigationBar(): Boolean {
        var hasNavigationBar: Boolean = false
        val res: Resources = mContext!!.resources
        val id: Int = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = res.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }
        return hasNavigationBar
    }
}