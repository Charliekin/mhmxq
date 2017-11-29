package com.mhm.xq.utils

import android.content.Context

class ScreenUtil {
    companion object {

        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }

        fun getScreenHeightWithoutStatusBar(context: Context): Int {
            return getScreenHeight(context) - StatusBarUtil.getStatusBarHeight(context)
        }
    }
}