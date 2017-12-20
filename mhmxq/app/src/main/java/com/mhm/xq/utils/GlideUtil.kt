package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.mhm.xq.MyApp
import com.mhm.xq.R
import com.mhm.xq.bll.DownloadManager
import com.mhm.xq.glide.GlideApp
import com.mhm.xq.glide.GlideRequest
import com.mhm.xq.glide.MyRequestOptions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class GlideUtil {

    companion object {
        fun loadImage(activity: Activity, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(activity).asBitmap().apply(MyRequestOptions.build(resId)).
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url).into(iv)
        }

        fun loadImage(fragment: Fragment, url: String, @DrawableRes resId: Int, iv: ImageView?) {
            GlideApp.with(fragment).asBitmap().apply(MyRequestOptions.build(resId)).
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url).into(iv)
        }

        fun loadImage(context: Context, url: String, @DrawableRes resId: Int, iv: ImageView) {
            GlideApp.with(context).asBitmap().apply(MyRequestOptions.build(resId)).
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url).into(iv)
        }

        fun loadLocalFromUri(activity: Activity, url: String, iv: ImageView) {
            GlideApp.with(activity).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).
                    load(url).centerCrop().into(iv)
        }

        fun loadLocalFromUri(fragment: Fragment, url: String, iv: ImageView?) {
            GlideApp.with(fragment).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).
                    load(url).centerCrop().into(iv)
        }

        fun loadLocalFromUri(context: Context, url: String, iv: ImageView) {
            GlideApp.with(context).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).
                    load(url).centerCrop().into(iv)
        }

        fun loadImage(activity: Activity, force: Boolean, url: String, view: View, width: Int, height: Int) {
            download(force, view, GlideApp.with(activity).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        fun loadImage(fragment: Fragment, force: Boolean, url: String, view: View, width: Int, height: Int) {
            download(force, view, GlideApp.with(fragment).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        fun loadImage(context: Context, force: Boolean, url: String, view: View, width: Int, height: Int) {
            download(force, view, GlideApp.with(context).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        fun downloadBitmap(fragment: Fragment, url: String, width: Int, height: Int) {
            download(true, null, GlideApp.with(fragment).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        fun downloadBitmap(activity: Activity, url: String, width: Int, height: Int) {
            download(true, null, GlideApp.with(activity).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        fun downloadBitmap(context: Context, url: String, width: Int, height: Int) {
            download(true, null, GlideApp.with(context).asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.NONE).load(url), width, height)
        }

        private fun download(force: Boolean, view: View?, glideRequest: GlideRequest<Bitmap>, width: Int, height: Int) {
            Observable.create(object : ObservableOnSubscribe<Bitmap> {
                override fun subscribe(e: ObservableEmitter<Bitmap>) {
                    if (width == 0 || height == 0) {
                        glideRequest.listener(requestListener(force, view, e)).submit().get()
                    } else {
                        glideRequest.listener(requestListener(force, view, e)).submit(width, height).get()
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        ToastUtil.show(MyApp.getContext()!!, R.string.save_success)
                    }, {
                        ToastUtil.show(MyApp.getContext()!!, R.string.common_error)
                    })
        }

        private fun requestListener(force: Boolean, view: View?, e: ObservableEmitter<Bitmap>): RequestListener<Bitmap> {
            return object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean =
                        false

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    if (force) {
                        saveImage(resource)
                        e.onNext(resource!!)
                        e.onComplete()
                    } else {
                        (view as SubsamplingScaleImageView).setImage(ImageSource.bitmap(resource))
                    }
                    return true
                }
            }
        }

        private fun saveImage(resource: Bitmap?) {
            var dir = DownloadManager.checkFolderExists(DownloadManager.projectPictureDir())
            var fileName = "" + System.currentTimeMillis() + ".jpg"
            var file = File(dir, fileName)
            if (file.exists()) {
                file.delete()
            }
            var os = FileOutputStream(file.absolutePath)
            resource!!.compress(Bitmap.CompressFormat.JPEG, 70, os)
        }
    }
}