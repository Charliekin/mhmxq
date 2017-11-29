package com.mhm.xq.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import com.mhm.xq.MyApp

class KeyboardUtil {
    companion object {
        var defaultKeyboardHeight: Int = 0
        var minKeyboardHeight: Int = 0
        var maxKeyboardHeight: Int = 0
        var maxFloatKeyboardHeight: Int = 0
        var statusBarHeight: Int = 0


        fun showKeyboard(view: View) {
            view.requestFocus()
            val imm = view.context.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }

        fun hideKeyboard(view: View?) {
            if (view == null) return
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }


        fun getKeyboardHeight(): Int {
            return KeyBoardSharedPreferences.getInstance()!!.get()
        }

        fun checkIsValidKeyboardHeight(height: Int): Boolean {
            if (height == statusBarHeight) return false
            if (height > minKeyboardHeight && height < maxKeyboardHeight) {
                return true
            }
            return if (height <= maxFloatKeyboardHeight && height > 0) {
                true
            } else false
        }

        fun attach(activity: Activity,
                   keyboardStatusChangedListener: OnKeyboardStatusChangedListener
        ): ViewTreeObserver.OnGlobalLayoutListener {
            val contentView = activity.findViewById<View>(android.R.id.content)
            val onKeyboardGlobalLayoutListener = OnKeyboardGlobalLayoutListener(activity, keyboardStatusChangedListener)
            contentView.viewTreeObserver.addOnGlobalLayoutListener(onKeyboardGlobalLayoutListener)
            return onKeyboardGlobalLayoutListener
        }

        fun detach(activity: Activity, onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener) {
            val contentView = activity.findViewById<View>(android.R.id.content)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
            } else {
                contentView.viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener)
            }
        }

        private class OnKeyboardGlobalLayoutListener : ViewTreeObserver.OnGlobalLayoutListener {
            private var mOnKeyboardStatusChangedListener: OnKeyboardStatusChangedListener? = null
            private var mContentView: ViewGroup? = null
            private var mIsFullScreen: Boolean = false
            private var mIsTranslucentStatus: Boolean = false
            private var mIsFitsSystemWindows: Boolean = false
            private var mStatusBarHeight: Int = 0
            private var mScreenHeight: Int = 0

            private var mIsOverlayLayoutDisplayHContainStatusBar: Boolean = false
            private var mLastDisplayHeight: Int = 0
            private var mLastKeyboardShowing: Boolean = false
            private var mMaxOverlayLayoutHeight = 0

            constructor(activity: Activity, keyboardStatusChangedListener: OnKeyboardStatusChangedListener) {
                mOnKeyboardStatusChangedListener = keyboardStatusChangedListener
                mContentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
                mIsFullScreen = ActivityUtil.isFullScreen(activity)
                mIsTranslucentStatus = ActivityUtil.isTranslucentStatus(activity)
                mIsFitsSystemWindows = ActivityUtil.isFitsSystemWindows(activity)
                mStatusBarHeight = StatusBarUtil.getStatusBarHeight(activity)
                mScreenHeight = ScreenUtil.getScreenHeight(activity)
            }

            override fun onGlobalLayout() {
                val rootView = mContentView!!.getChildAt(0)
                val actionBarOverlayLayout = mContentView!!.getParent() as View
                val rect = Rect()
                val displayHeight: Int
                if (mIsTranslucentStatus) {
                    actionBarOverlayLayout.getWindowVisibleDisplayFrame(rect)
                    val overlayLayoutDisplayHeight = rect.bottom - rect.top
                    if (!mIsOverlayLayoutDisplayHContainStatusBar) {
                        mIsOverlayLayoutDisplayHContainStatusBar = overlayLayoutDisplayHeight == mScreenHeight
                    }
                    if (!mIsOverlayLayoutDisplayHContainStatusBar) {
                        displayHeight = overlayLayoutDisplayHeight + mStatusBarHeight
                    } else {
                        displayHeight = overlayLayoutDisplayHeight
                    }
                } else {
                    if (rootView == null) return
                    rootView.getWindowVisibleDisplayFrame(rect)
                    displayHeight = rect.bottom - rect.top
                }
                calculateKeyboardHeight(displayHeight)
                calculateKeyboardShowing(displayHeight)
            }

            private fun calculateKeyboardHeight(displayHeight: Int) {
                if (mLastDisplayHeight == 0) {
                    mLastDisplayHeight = displayHeight
                    if (mOnKeyboardStatusChangedListener != null) {
                        mOnKeyboardStatusChangedListener!!.onKeyboardHeightChanged(
                                KeyBoardSharedPreferences.getInstance()!!.get())
                    }
                    return
                }
                val keyboardHeight = Math.abs(displayHeight - mLastDisplayHeight)
                if (!checkIsValidKeyboardHeight(keyboardHeight)) {
                    return
                }
                val changed = KeyBoardSharedPreferences.getInstance()!!.save(keyboardHeight)
                if (changed) {
                    if (mOnKeyboardStatusChangedListener != null) {
                        mOnKeyboardStatusChangedListener!!.onKeyboardHeightChanged(
                                KeyBoardSharedPreferences.getInstance()!!.get())
                    }
                }
            }

            private fun calculateKeyboardShowing(displayHeight: Int) {
                val isKeyboardShowing: Boolean
                val actionBarOverlayLayout = mContentView!!.getParent() as View
                val actionBarOverlayLayoutHeight = actionBarOverlayLayout.height - actionBarOverlayLayout.paddingTop
                if (mMaxOverlayLayoutHeight == 0) {
                    isKeyboardShowing = mLastKeyboardShowing
                } else {
                    isKeyboardShowing = checkIsValidKeyboardHeight(mMaxOverlayLayoutHeight - displayHeight)
                }
                mMaxOverlayLayoutHeight = Math.max(mMaxOverlayLayoutHeight, actionBarOverlayLayoutHeight)
                if (mLastKeyboardShowing != isKeyboardShowing && mOnKeyboardStatusChangedListener != null) {
                    mOnKeyboardStatusChangedListener!!.onKeyboardVisibleChanged(isKeyboardShowing)
                }
                mLastKeyboardShowing = isKeyboardShowing
            }
        }


        private class KeyBoardSharedPreferences {
            private val mSp: SharedPreferences
            private var mLastSaveKeyboardHeight = 0

            companion object {
                private val FILE_NAME = "keyboard.sp"
                private val KEY_KEYBOARD_HEIGHT = "KEYBOARD_HEIGHT"

                @Volatile internal var instance: KeyBoardSharedPreferences? = null
                fun getInstance(): KeyBoardSharedPreferences? {
                    synchronized(KeyBoardSharedPreferences::class) {
                        if (instance == null) {
                            instance = KeyBoardSharedPreferences()
                        }
                    }
                    return instance
                }
            }

            private constructor() {
                mSp = MyApp.getContext()!!.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            }

            fun save(keyboardHeight: Int): Boolean {
                if (mLastSaveKeyboardHeight == keyboardHeight || keyboardHeight < 0) {
                    return false
                }
                val success = mSp.edit().putInt(KEY_KEYBOARD_HEIGHT, keyboardHeight).commit()
                if (success) {
                    mLastSaveKeyboardHeight = keyboardHeight
                    return true
                }
                return false
            }

            operator fun get(defaultHeight: Int): Int {
                return if (mLastSaveKeyboardHeight != 0) {
                    mLastSaveKeyboardHeight
                } else mSp.getInt(KEY_KEYBOARD_HEIGHT, defaultHeight)
            }

            fun get(): Int {
                return get(defaultKeyboardHeight)
            }
        }
    }

    init {
        defaultKeyboardHeight = DensityUtil.dip2px(MyApp.getContext()!!, 220F)
        minKeyboardHeight = DensityUtil.dip2px(MyApp.getContext()!!, 80F)
        maxKeyboardHeight = DensityUtil.dip2px(MyApp.getContext()!!, 380F)
        maxFloatKeyboardHeight = DensityUtil.dip2px(MyApp.getContext()!!, 1F)
        statusBarHeight = StatusBarUtil.getStatusBarHeight(MyApp.getContext()!!)
    }

    interface OnKeyboardStatusChangedListener {

        fun onKeyboardVisibleChanged(isVisible: Boolean)

        fun onKeyboardHeightChanged(height: Int)

    }
}