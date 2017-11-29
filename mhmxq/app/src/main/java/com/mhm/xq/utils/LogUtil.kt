package com.mhm.xq.utils

import android.text.TextUtils
import android.util.Log
import com.mhm.xq.BuildConfig

class LogUtil {
    companion object {
        var customTagPrefix = ""
        var allowD = true
        var allowE = true
        var allowI = true
        var allowV = true
        var allowW = true
        var allowWtf = true

        private fun generateTag(caller: StackTraceElement): String {
            var tag = "%s.%s(L:%d)"
            var callerClazzName = caller.className
            callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1)
            tag = String.format(tag, callerClazzName, caller.methodName, caller.lineNumber)
            tag = if (TextUtils.isEmpty(customTagPrefix)) tag else customTagPrefix + ":" + tag
            return tag
        }

        var customLogger: CustomLogger? = null

        interface CustomLogger {
            fun d(tag: String, content: String)

            fun d(tag: String, content: String, tr: Throwable)

            fun e(tag: String, content: String)

            fun e(tag: String, content: String, tr: Throwable)

            fun i(tag: String, content: String)

            fun i(tag: String, content: String, tr: Throwable)

            fun v(tag: String, content: String)

            fun v(tag: String, content: String, tr: Throwable)

            fun w(tag: String, content: String)

            fun w(tag: String, content: String, tr: Throwable)

            fun w(tag: String, tr: Throwable)

            fun wtf(tag: String, content: String)

            fun wtf(tag: String, content: String, tr: Throwable)

            fun wtf(tag: String, tr: Throwable)
        }

        fun d(content: String) {
            if (!BuildConfig.DEBUG || !allowD) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.d(tag, content)
            } else {
                Log.d(tag, content)
            }
        }

        fun d(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowD) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.d(tag, content, tr)
            } else {
                Log.d(tag, content, tr)
            }
        }

        fun e(content: String?) {
            var content = content
            if (!BuildConfig.DEBUG || !allowE) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.e(tag, content)
            } else {
                Log.e(tag, content)
            }
        }

        fun e(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowE) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.e(tag, content, tr)
            } else {
                Log.e(tag, content, tr)
            }
        }

        fun i(content: String?) {
            var content = content
            if (!BuildConfig.DEBUG || !allowI) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.i(tag, content)
            } else {
                Log.i(tag, content)
            }
        }

        fun i(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowI) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.i(tag, content, tr)
            } else {
                Log.i(tag, content, tr)
            }
        }

        fun v(content: String?) {
            var content = content
            if (!BuildConfig.DEBUG || !allowV) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.v(tag, content)
            } else {
                Log.v(tag, content)
            }
        }

        fun v(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowV) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.v(tag, content, tr)
            } else {
                Log.v(tag, content, tr)
            }
        }

        fun w(content: String?) {
            var content = content
            if (!BuildConfig.DEBUG || !allowW) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.w(tag, content)
            } else {
                Log.w(tag, content)
            }
        }

        fun w(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowW) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.w(tag, content, tr)
            } else {
                Log.w(tag, content, tr)
            }
        }

        fun w(tr: Throwable) {
            if (!BuildConfig.DEBUG || !allowW) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (customLogger != null) {
                customLogger!!.w(tag, tr)
            } else {
                Log.w(tag, tr)
            }
        }


        fun wtf(content: String?) {
            var content = content
            if (!BuildConfig.DEBUG || !allowWtf) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.wtf(tag, content)
            } else {
                Log.wtf(tag, content)
            }
        }

        fun wtf(content: String?, tr: Throwable) {
            var content = content
            if (!BuildConfig.DEBUG || !allowWtf) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)
            if (content == null) {
                content = "null"
            }
            if (customLogger != null) {
                customLogger!!.wtf(tag, content, tr)
            } else {
                Log.wtf(tag, content, tr)
            }
        }

        fun wtf(tr: Throwable) {
            if (!BuildConfig.DEBUG || !allowWtf) return
            val caller = ThreadUtil.getCallerStackTraceElement()
            val tag = generateTag(caller)

            if (customLogger != null) {
                customLogger!!.wtf(tag, tr)
            } else {
                Log.wtf(tag, tr)
            }
        }
    }
}