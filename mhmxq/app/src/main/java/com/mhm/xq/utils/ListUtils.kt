package com.mhm.xq.utils

import java.util.*

class ListUtils {

    companion object {
        fun <T> convertToList(arr: Array<T>?): List<T> {
            return if (arr == null) {
                ArrayList()
            } else {
                ArrayList(Arrays.asList(*arr))
            }
        }

        fun <T> dropLastWhile(lstData: MutableList<T>, callback: OnPredicateCallback<T>): Int {
            var targetIndex = 0
            for (index in lstData.indices.reversed()) {
                if (!callback.predicate(lstData[index])) {
                    targetIndex = index + 1
                    break
                }
            }
            if (targetIndex < lstData.size) {
                for (removeIndex in lstData.size - 1 downTo targetIndex) {
                    lstData.removeAt(removeIndex)
                }
            } else {
                targetIndex = -1
            }
            return targetIndex
        }

        fun <T> dropWhile(lstData: MutableList<T>, callback: OnPredicateCallback<T>): Int {
            var targetIndex = -1
            for (item in lstData) {
                if (callback.predicate(item)) {
                    targetIndex += 1
                    break
                } else {
                    break
                }
            }
            if (targetIndex < lstData.size) {
                for (removeIndex in 0..targetIndex) {
                    lstData.removeAt(removeIndex)
                }
            } else {
                targetIndex = -1
            }
            return targetIndex
        }


    }

    public interface OnPredicateCallback<T> {

        fun predicate(data: T): Boolean

    }
}