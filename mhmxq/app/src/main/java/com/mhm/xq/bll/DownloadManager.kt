package com.mhm.xq.bll

import android.app.Activity
import android.os.Environment
import com.mhm.xq.BuildConfig
import com.mhm.xq.utils.GlideUtil
import com.mhm.xq.utils.StringUtil
import java.io.File
import java.lang.StringBuilder

class DownloadManager {
    companion object {
        private fun projectExternalStorageDirectory(): StringBuilder {
            var sb = StringBuilder()
            sb.append(Environment.getExternalStorageDirectory().path)
            if (BuildConfig.DEBUG) {
                sb.append("/mhmtest")
            } else {
                sb.append("/mhm")
            }
            return sb
        }

        fun projectPictureDir(): String {
            return projectExternalStorageDirectory()
                    .append("/download/picture").toString()
        }

        fun checkFolderExists(path: String): File? {
            if (StringUtil.isEmpty(path)) {
                return null
            }
            var file = File(path)
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        fun saveImage(activity: Activity, url: String, width: Int, height: Int) {
            GlideUtil.downloadBitmap(activity, url, width, height)
        }
    }
}