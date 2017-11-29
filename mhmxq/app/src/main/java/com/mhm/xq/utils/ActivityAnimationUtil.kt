package com.mhm.xq.utils

import android.app.Activity
import android.content.Context

class ActivityAnimationUtil {
    companion object {
        fun fadeAnimation(context: Context) {
            val activity = ContextUtil.convertToActivity(context)
            if (activity != null) {
                (context as Activity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }


        fun dontAnimation(context: Context) {
            val activity = ContextUtil.convertToActivity(context)
            if (activity != null) {
                (context as Activity).overridePendingTransition(0, 0)
            }
        }
    }
}