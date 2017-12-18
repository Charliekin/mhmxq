package com.mhm.xq.ui.base.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.IntDef
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.*
import android.support.design.widget.BottomSheetDialog
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mhm.xq.R
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class FixStatusBarBottomSheetDialog : BottomSheetDialog {

    @State
    private var mState = STATE_COLLAPSED

    constructor(context: Context) : super(context) {
        initTheme()
    }

    constructor(context: Context, theme: Int) : super(context, theme) {
        initTheme()
    }

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) :
            super(context, cancelable, cancelListener) {
        initTheme()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var screenHeight = getScreenHeight(ownerActivity)
        var statusBarHeight = getStatusBarHeight(context)
        var dialogHeight = screenHeight - statusBarHeight
        if (window != null) {
            var height = ViewGroup.LayoutParams.MATCH_PARENT
            if (dialogHeight != 0) {
                height = dialogHeight
            }
            window.setGravity(Gravity.BOTTOM)
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
        }
    }

    override fun setContentView(layoutResId: Int) {
        var view: View = LayoutInflater.from(context).inflate(layoutResId, null)
        super.setContentView(view)
        initBehavior(view)
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initBehavior(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        initBehavior(view)
    }

    private fun initTheme() {
        val a = context.theme.obtainStyledAttributes(R.styleable.FixStatusBarBottomSheetDialog)
        val stateIndex = a.getInt(R.styleable.FixStatusBarBottomSheetDialog_bottomSheetDialogState, 1)
        if (stateIndex in 0..2) {
            mState = STATES[stateIndex]
        }
        a.recycle()
    }

    private fun initBehavior(contentView: View?) {
        val parent = contentView!!.parent as View
        val behavior = BottomSheetBehavior.from(parent)
        behavior.state = mState
    }

    private fun getScreenHeight(activity: Activity?): Int {
        val displaymetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.heightPixels
    }

    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    @IntDef(STATE_EXPANDED.toLong(), STATE_COLLAPSED.toLong(), STATE_HIDDEN.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class State

    private val STATES = intArrayOf(STATE_EXPANDED, STATE_COLLAPSED, STATE_HIDDEN)
}