package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mhm.xq.R
import com.mhm.xq.glide.GlideApp

class GlideUtil {

    companion object {
        fun loadImage(activity: Activity, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(activity).asBitmap().apply(requestOptions(resId)).load(url).into(iv)
        }

        fun loadImage(fragment: Fragment, url: String, @DrawableRes resId: Int, iv: ImageView?) {
            GlideApp.with(fragment).asBitmap().apply(requestOptions(resId)).load(url).into(iv)
        }

        fun loadImage(context: Context, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(context).asBitmap().apply(requestOptions(resId)).load(url).into(iv)
        }

        private fun requestOptions(@DrawableRes resId: Int): RequestOptions {
            return RequestOptions()
                    .centerCrop()
                    .placeholder(resId)
                    .error(R.mipmap.ic_launcher)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
        }


    }
}