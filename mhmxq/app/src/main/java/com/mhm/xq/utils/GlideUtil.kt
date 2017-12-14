package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.mhm.xq.glide.GlideApp
import com.mhm.xq.glide.MyRequestOptions

class GlideUtil {

    companion object {
        fun loadImage(activity: Activity, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(activity).asBitmap().apply(MyRequestOptions.build(resId)).load(url).into(iv)
        }

        fun loadImage(fragment: Fragment, url: String, @DrawableRes resId: Int, iv: ImageView?) {
            GlideApp.with(fragment).asBitmap().apply(MyRequestOptions.build(resId)).load(url).into(iv)
        }

        fun loadImage(context: Context, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(context).asBitmap().apply(MyRequestOptions.build(resId)).load(url).into(iv)
        }
    }
}