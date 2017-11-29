package com.mhm.xq.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtil {
    companion object {
        private var mPreferences: SharedPreferences? = null
        fun init(context: Context) {
            init(context, "DEFAULT_PREFERENCE")
        }

        fun init(context: Context, name: String) {
            mPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE or Context.MODE_MULTI_PROCESS)
        }

        fun putString(key: String, value: String): Boolean {
            val editor = mPreferences!!.edit()
            editor.putString(key, value)
            return editor.commit()
        }

        fun getString(key: String): String {
            return getString(key, null.toString())
        }

        fun getString(key: String, defaultValue: String): String {
            return mPreferences!!.getString(key, defaultValue)
        }

        fun putInt(key: String, value: Int): Boolean {
            val editor = mPreferences!!.edit()
            editor.putInt(key, value)
            return editor.commit()
        }

        fun getInt(key: String): Int {
            return getInt(key, -1)
        }

        fun getInt(key: String, defaultValue: Int): Int {
            return mPreferences!!.getInt(key, defaultValue)
        }

        fun putLong(key: String, value: Long): Boolean {
            val editor = mPreferences!!.edit()
            editor.putLong(key, value)
            return editor.commit()
        }

        fun getLong(key: String): Long {
            return getLong(key, -1L)
        }

        fun getLong(key: String, defaultValue: Long): Long {
            return mPreferences!!.getLong(key, defaultValue)
        }

        fun putFloat(key: String, value: Float): Boolean {
            val editor = mPreferences!!.edit()
            editor.putFloat(key, value)
            return editor.commit()
        }

        fun getFloat(key: String): Float {
            return getFloat(key, -1.0f)
        }

        fun getFloat(key: String, defaultValue: Float): Float {
            return mPreferences!!.getFloat(key, defaultValue)
        }

        fun putBoolean(key: String, value: Boolean): Boolean {
            val editor = mPreferences!!.edit()
            editor.putBoolean(key, value)
            return editor.commit()
        }

        fun getBoolean(key: String): Boolean {
            return getBoolean(key, false)
        }

        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return mPreferences!!.getBoolean(key, defaultValue)
        }
    }
}