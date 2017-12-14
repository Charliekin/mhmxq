package com.mhm.xq.glide

import android.support.annotation.DrawableRes
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class MyRequestOptions : RequestOptions() {

    companion object {
        fun build(@DrawableRes resId: Int): MyRequestOptions {
            return MyRequestOptions().centerCrop().placeholder(resId)
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) as MyRequestOptions
        }

        fun build(@DrawableRes resId: Int, @DrawableRes errorResId: Int): MyRequestOptions {
            return MyRequestOptions().centerCrop().placeholder(resId)
                    .error(errorResId)
                    .priority(Priority.NORMAL)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) as MyRequestOptions
        }
    }

}