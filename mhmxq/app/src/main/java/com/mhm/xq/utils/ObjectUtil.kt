package com.mhm.xq.utils

class ObjectUtil {
    companion object {
        fun isEquals(actual: Any?, expected: Any?): Boolean {
            return actual === expected || if (actual == null) expected == null else actual == expected
        }

    }
}