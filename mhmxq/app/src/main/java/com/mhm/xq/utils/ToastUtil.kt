package com.mhm.xq.utils

import android.content.Context
import android.widget.Toast
import com.mhm.xq.MyApp

class ToastUtil {

    companion object {

        fun show(context: Context?, msg: String) {
            var context = context
            if (StringUtil.isBlank(msg)) {
                return
            }
            if (context == null) {
                context = MyApp.getContext()
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun show(context: Context, resId: Int) {
            show(context, MyApp.getContext()!!.getString(resId))
        }
    }
}