package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class SoftInputUtils {
    companion object {
        public fun hide(activity: Activity) {
            var imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        public fun show(activity: Activity, view: View) {
            var imm: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (view.requestFocus())
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}