package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v4.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout

class StatusBarUtil {
    companion object {
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resId > 0) {
                result = context.resources.getDimensionPixelOffset(resId)
            }
            return result
        }


        fun translucentStatusBar(activity: Activity, hideStatusBarBackground: Boolean, isSupportKitkat: Boolean) {
            if (!isSupportKitkat && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                return
            val window = activity.window
            val contentView = activity.findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
            var childView: View? = contentView.getChildAt(0)
            if (childView != null) {
                ViewCompat.setFitsSystemWindows(childView, false)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val statusBarHeight = getStatusBarHeight(activity)
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    if (!hideStatusBarBackground) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.statusBarColor = Color.parseColor("#00000000")
                    } else {
                        window.statusBarColor = Color.parseColor("#66000000")
                    }
                    if (childView != null) {
                        ViewCompat.requestApplyInsets(childView)
                    }
                } else {
                    if (childView != null && childView.layoutParams != null && childView.layoutParams.height == statusBarHeight) {
                        contentView.removeView(childView)
                        childView = contentView.getChildAt(0)
                    }
                    if (childView != null) {
                        val lp = childView.layoutParams
                        if (lp != null && lp is FrameLayout.LayoutParams) {
                            if (lp.topMargin >= statusBarHeight) {
                                lp.topMargin -= statusBarHeight
                                childView.requestLayout()
                            }
                        }
                    }
                }
            }
        }

    }
}