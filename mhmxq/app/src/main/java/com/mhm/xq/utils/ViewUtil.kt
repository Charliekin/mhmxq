package com.mhm.xq.utils

import android.content.Context
import com.mhm.xq.MyApp

class ViewUtil {
    companion object {
        private var displayDensity: Float? = null

        fun roundUp(paramFloat: Float): Int {
            return (0.5f + paramFloat).toInt()
        }

        fun dipToPx(paramContext: Context, paramFloat: Float): Int {
            return roundUp(getDisplayDensity(paramContext) * paramFloat)
        }

        fun pxToDip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        private fun getDisplayDensity(paramContext: Context): Float {
            if (displayDensity == null)
                displayDensity = java.lang.Float.valueOf(paramContext.resources.displayMetrics.density)
            return displayDensity!!.toFloat()
        }

        fun getDisplayHeight(paramContext: Context): Int {
            return paramContext.resources.displayMetrics.heightPixels
        }

        fun getDisplayWidth(paramContext: Context): Int {
            return paramContext.resources.displayMetrics.widthPixels
        }

        fun getStatusBarHeight(): Int {
            var result = 0
            var res = 0
            res = MyApp.getContext()!!.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (res > 0) {
                result = MyApp.getContext()!!.resources.getDimensionPixelSize(res)
            }

            return result
        }
    }
}