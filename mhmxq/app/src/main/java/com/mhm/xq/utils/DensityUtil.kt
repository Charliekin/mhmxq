package com.mhm.xq.utils

import android.content.Context
import android.util.TypedValue

class DensityUtil {
    companion object {
        public fun dip2px(context: Context, dipValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return ((dipValue * scale + 0.5f).toInt())
        }

        public fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return ((pxValue / scale + 0.5f).toInt())
        }

        public fun px2sp(context: Context, pxValue: Float): Float {
            return (pxValue / context.resources.displayMetrics.scaledDensity)
        }

        public fun sp2px(context: Context, spVlaue: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVlaue.toFloat(),
                    context.resources.displayMetrics).toInt()
        }
    }
}