package com.mhm.xq.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class AppUtil {
    companion object {
        /**
         * 获取app版本名
         */
        fun getAppVersionName(context: Context): String {
            val pm = context.packageManager
            val pi: PackageInfo
            try {
                pi = pm.getPackageInfo(context.packageName, 0)
                return pi.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return ""
        }

        /**
         * 获取app版本号
         */
        fun getAppVersionCode(context: Context): Int {
            val pm = context.packageManager
            val pi: PackageInfo
            try {
                pi = pm.getPackageInfo(context.packageName, 0)
                return pi.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return 0
        }

        /**
         * 获得当前进程名称
         */
        fun getCurrentProcessName(context: Context): String? {
            val pid = android.os.Process.myPid()
            val mActivityManager = context
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (appProcess in mActivityManager
                    .runningAppProcesses) {
                if (appProcess.pid == pid) {
                    return appProcess.processName
                }
            }
            return null
        }

        fun getMetaValue(context: Context, name: String): String? {
            var appInfo: ApplicationInfo? = null
            try {
                appInfo = context.packageManager.getApplicationInfo(context.packageName,
                        PackageManager.GET_META_DATA)
                return appInfo!!.metaData.getString(name)
            } catch (e: PackageManager.NameNotFoundException) {
                return null
            }

        }
    }
}