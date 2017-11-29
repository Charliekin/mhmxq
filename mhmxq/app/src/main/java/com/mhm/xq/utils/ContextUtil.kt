package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

class ContextUtil {
    companion object {
        fun convertToActivity(ctx: Context): Activity? {
            var ctx = ctx
            if (ctx is Activity) {
                return ctx
            } else if (ctx is ContextWrapper) {
                ctx = ctx.baseContext
                if (ctx is Activity) {
                    return ctx
                }
            }
            LogUtil.e("convert activity fail")
            return null
        }
    }
}