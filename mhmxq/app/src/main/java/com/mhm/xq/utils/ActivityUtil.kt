package com.mhm.xq.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

class ActivityUtil {
    companion object {


        fun isFullScreen(activity: Activity): Boolean {
            return activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != 0
        }

        fun isTranslucentStatus(activity: Activity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS != 0
            } else false
        }

        fun isLayoutFullscreen(activity: Activity): Boolean {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                    activity.window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN != 0
        }

        fun isFitsSystemWindows(activity: Activity): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                val rootView = activity.findViewById<View>(android.R.id.content) as ViewGroup
                if (rootView != null) {
                    return rootView.getChildAt(0).fitsSystemWindows
                }
            }
            return false
        }

        fun findRootView(activity: Activity): ViewGroup {
            return activity.window.decorView.findViewById<View>(android.R.id.content) as ViewGroup
        }
    }
}