package com.mhm.xq.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.mhm.xq.MyApp
import java.net.UnknownHostException

class NetUtil {
    companion object {
        @SuppressLint("MissingPermission")
        fun isNetEnabled(): Boolean {
            val connectivityManager = MyApp.getContext()!!.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            try {
                return if (connectivityManager == null || connectivityManager.activeNetworkInfo == null
                        || !connectivityManager.activeNetworkInfo.isAvailable) {
                    false
                } else {
                    true
                }
            } catch (ex: Exception) {
                return false
            }

        }

        fun isNetworkConnected(context: Context, throwable: Throwable?): Boolean {
            try {
                var isConnected = isNetEnabled()
                if (isConnected) {
                    if (throwable != null) {
                        isConnected = throwable !is UnknownHostException
                    }
                }
                return isConnected
            } catch (ex: Exception) {
                return false
            }

        }

        @SuppressLint("MissingPermission")
                /**
                 * 检查当前是否是wifi网络
                 *
                 * @param context 上下文
                 * @return true / false
                 */
        fun isWifiNetworkType(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            try {
                if (manager == null) {
                    return false
                }
                val networkInfo = manager.activeNetworkInfo ?: return false
                return if (networkInfo.isAvailable && networkInfo.typeName.equals("wifi", ignoreCase = true)) {
                    true
                } else false
            } catch (ex: Exception) {
                return false
            }

        }
    }
}