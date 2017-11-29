package com.mhm.xq.utils

import android.os.Looper

class ThreadUtil {
    companion object {
        fun getCallerStackTraceElement(): StackTraceElement {
            return Thread.currentThread().stackTrace[4]
        }

        /**
         * 是否在主线程
         */
        fun isOnMainThread(): Boolean {
            return Looper.myLooper() == Looper.getMainLooper()
        }

        /**
         * 是否在后台线程
         */
        fun isOnBackgroundThread(): Boolean {
            return !isOnMainThread()
        }

        /**
         * 如果不在主线程,就会抛出异常
         */
        fun assertMainThread() {
            if (!isOnMainThread()) {
                throw IllegalArgumentException("You must call this method on the main thread")
            }
        }

        /**
         * 如果不在后台线程,就会抛出异常
         */
        fun assertBackgroundThread() {
            if (!isOnBackgroundThread()) {
                throw IllegalArgumentException("YOu must call this method on a background thread")
            }
        }
    }
}