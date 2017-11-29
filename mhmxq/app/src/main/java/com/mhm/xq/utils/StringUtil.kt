package com.mhm.xq.utils

import android.text.TextUtils

class StringUtil {
    companion object {
        /**
         * 如果为null或tim后的长度为0时返回True, 否则返回False
         *
         * @param str 字符串
         * @return True / False
         */
        fun isBlank(str: CharSequence?): Boolean {
            return str == null || str.toString().trim { it <= ' ' }.length == 0
        }


        /**
         * 如果为null或长度为0时返回True, 否则返回False
         *
         * @param str 字符串
         * @return True / False
         */
        fun isEmpty(str: CharSequence?): Boolean {
            return str == null || str.length == 0
        }

        /**
         * 是否是英文字符
         *
         * @param i
         * @return
         */
        fun isEnglishChar(i: Int): Boolean {
            return if (i >= 97 && i <= 122 || i >= 65 && i <= 90) {
                true
            } else {
                false
            }
        }


        fun isEquals(actual: String, expected: String): Boolean {
            return ObjectUtil.isEquals(actual, expected)
        }

        fun getLastWords(srcText: String, p: String): String? {
            try {
                val array = TextUtils.split(srcText, p)
                val index = if (array.size - 1 < 0) 0 else array.size - 1
                return array[index]
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun trimTrailingWhitespace(source: CharSequence?): CharSequence {
            if (source == null)
                return ""

            var i = source.length

            // loop back to the first non-whitespace character
            while (--i >= 0 && Character.isWhitespace(source[i])) {
            }

            return source.subSequence(0, i + 1)
        }
    }
}